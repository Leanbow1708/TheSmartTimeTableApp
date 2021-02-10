package com.mayank.mytimetable.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.DataClass.SavedNotes;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Utils.Constants;
import com.mayank.mytimetable.Utils.MillisToHour;
import com.mayank.mytimetable.Utils.MillisToMin;
import com.mayank.mytimetable.Utils.OnChartClickListener;
import com.mayank.mytimetable.ViewHolders.DayProgressViewHolder;
import com.mayank.mytimetable.ViewHolders.LoadingViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProgressDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ProgressDayAdapter";
    List<SavedNotes> notesList = new ArrayList<>();
    OnChartClickListener onChartClickListener;
    static int turn = 1;
    int curr_day = 0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if(turn == 1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_day_progress, parent, false);
            return new DayProgressViewHolder(view);
        }
        else{

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_placeholder_curday_progress, parent, false);
            return new LoadingViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        getItemViewType(holder.getAdapterPosition());
        if(turn == 1)
        {


            int estTimeMillis = 0;


            int startTimeMillis = notesList.get(holder.getAdapterPosition()).getSetStartTime();
            int endTimeMillis = notesList.get(holder.getAdapterPosition()).getSetEndTime();
            int studyEndTimeMillis = notesList.get(holder.getAdapterPosition()).getEndEndTime();
            final int studyStartTimeMillis = notesList.get(holder.getAdapterPosition()).getEndStartTime();

            int totalStudyMin;
            int totalSetStudyDuration = 0;
            Constants.currentTime24Form();
            int curTimeMillis = Constants.hr24*60 + Constants.min;

            if(studyStartTimeMillis == -1 && curTimeMillis > endTimeMillis)
            {
                ((DayProgressViewHolder)holder).txtMissed.setText("Missed !!!");
                ((DayProgressViewHolder)holder).txtMissed.setTextColor(Color.RED);


                ((DayProgressViewHolder)holder).txtMissed.setVisibility(View.VISIBLE);
            }
            else{
                if(curTimeMillis < startTimeMillis && curr_day == Constants.day-1)
                {
                    ((DayProgressViewHolder)holder).txtMissed.setText("Upcoming");
                    ((DayProgressViewHolder)holder).txtMissed.setTextColor(Color.GRAY);

                    ((DayProgressViewHolder)holder).txtMissed.setVisibility(View.VISIBLE);

                }
                else
                ((DayProgressViewHolder)holder).txtMissed.setVisibility(View.GONE);

            }

            if(studyEndTimeMillis == -1 || studyEndTimeMillis>endTimeMillis)
                studyEndTimeMillis = endTimeMillis;
            int breakTaken = notesList.get(holder.getAdapterPosition()).getBreakAmt();


            totalStudyMin = studyEndTimeMillis-studyStartTimeMillis-breakTaken;
            totalStudyMin = Math.max(0,totalStudyMin);
            totalSetStudyDuration = endTimeMillis-startTimeMillis;

            final int finalTotalSetStudyDuration = totalSetStudyDuration;
            final int finalTotalStudyMin = totalStudyMin;
            final int finalTotalSetStudyDuration1 = totalSetStudyDuration;
            final int finalTotalStudyMin1 = totalStudyMin;
            ((DayProgressViewHolder)holder).imgChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<notesList.size())
                {
                    Constants.currentTime24Form();


                    if(studyStartTimeMillis!=-1)
                        onChartClickListener.onChartClickListener(finalTotalSetStudyDuration, finalTotalStudyMin, finalTotalSetStudyDuration1 - finalTotalStudyMin1);
                }

                }
            });
            //check if the task is started,if started then within valid time or not
            if(studyStartTimeMillis!=-1 && studyStartTimeMillis>= startTimeMillis && studyStartTimeMillis<= endTimeMillis)
            {






                Log.d(TAG, "onBindViewHolder: "+notesList.get(holder.getAdapterPosition()).getSubject()+" total study "+totalStudyMin+" total alloted "+totalSetStudyDuration);

                 int studyPercent = (totalStudyMin*100/totalSetStudyDuration);

                ((DayProgressViewHolder)holder).progressBar.setProgress(studyPercent);
                ((DayProgressViewHolder)holder).txtPercentProgress.setText(studyPercent+"%");

                if(studyPercent >= 75) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setTint(Color.parseColor("#b1fcc1"));
                    }
                    ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setLevel(studyPercent * 100);
                }
                else if(studyPercent>=35 && studyPercent < 75 )
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setTint(Color.parseColor("#ffe596"));
                    }
                    ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setLevel(studyPercent * 100);
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setTint(Color.parseColor("#ffa8a8"));
                    }
                    ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setLevel(100 * 100);
                }

             }
            else{
                ((DayProgressViewHolder) holder).layoutPrimary.getBackground().setLevel(0 * 100);

                ((DayProgressViewHolder)holder).progressBar.setProgress(0);
                ((DayProgressViewHolder)holder).txtPercentProgress.setText(0+"%");
            }









            if(startTimeMillis > endTimeMillis)
            {
                estTimeMillis = 24*60 - startTimeMillis + endTimeMillis;
            }
            else{
                estTimeMillis = endTimeMillis - startTimeMillis;
            }
            String hr1 = MillisToHour.convertToString(estTimeMillis);
            String min1 = MillisToMin.convertToString(estTimeMillis);


            String startHour = MillisToHour.convertToString(startTimeMillis);
            String startMin = MillisToMin.convertToString(startTimeMillis);


            String endHour = MillisToHour.convertToString(endTimeMillis);
            String endMin = MillisToMin.convertToString(endTimeMillis);


            ((DayProgressViewHolder)holder).txtTimeSlot.setText(startHour+"."+startMin+" - "+endHour+"."+endMin);





            ((DayProgressViewHolder)holder).txtTotalDuration.setText("Total duration - "+hr1+" hr "+min1+" min");


            ((DayProgressViewHolder)holder).txtSubject.setText(notesList.get(holder.getAdapterPosition()).getSubject());

         


        }



    }

    @Override
    public int getItemViewType(int position) {
        if(notesList.get(position).getDay().equals("day"))
        {
            turn = 2;
            return 2;
        }
        else{
            turn = 1;
            return 1;
        }

    }

    public void setOnChartClickListener(OnChartClickListener clickListener)
    {
        onChartClickListener = clickListener;
    }
    public void setCurDay(int i)
    {
        curr_day = i;
    }

    public void setNotesList(List<SavedNotes> list)
    {
        notesList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(notesList == null)
            return 0;
        else
        return notesList.size();
    }
}
