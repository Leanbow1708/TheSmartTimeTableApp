package com.mayank.mytimetable.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Utils.Constants;
import com.mayank.mytimetable.Utils.MillisToHour;
import com.mayank.mytimetable.Utils.MillisToMin;
import com.mayank.mytimetable.Utils.OnAddBreakClickListener;
import com.mayank.mytimetable.Utils.OnStartEndClickListener;
import com.mayank.mytimetable.ViewHolders.CurrDayHomeTimeTableViewHolder;
import com.mayank.mytimetable.ViewHolders.LoadingViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CurrDayTimeTableAdapter extends RecyclerView.Adapter {

    private OnStartEndClickListener startEndClickListener;
    private List<Note> noteList = new ArrayList<>();
    private static final String TAG = "CurrDayTimeTableAdapter";
    private OnAddBreakClickListener addBreakClickListener;
    String finalHaveHad;
    int startTimeMillis;
    int studyStartTime;
    int studyEndTime;

    int endTimeMillis;
    public static int turn = 1;

    public CurrDayTimeTableAdapter() {


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        shimmerFrameLayout.stopShimmerAnimation();

        View view;
        switch (turn) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_curr_day_recycler, parent, false);
                return new CurrDayHomeTimeTableViewHolder(view);
            case 2:   view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_placeholder_curday_progress, parent, false);
                return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        getItemViewType(holder.getAdapterPosition());


        if(turn == 1) {


            Constants.currentTime24Form();
            int curTimeMillis = Constants.hr24 * 60 + Constants.min;


            endTimeMillis = -1;
            startTimeMillis = -1;

            finalHaveHad = "has";


            studyStartTime = noteList.get(holder.getAdapterPosition()).getEndStartTime();
            studyEndTime = noteList.get(holder.getAdapterPosition()).getEndEndTime();
            startTimeMillis = noteList.get(holder.getAdapterPosition()).getSetStartTime();
            endTimeMillis = noteList.get(holder.getAdapterPosition()).getSetEndTime();


            String startHour = MillisToHour.convertToString(startTimeMillis);
            String startMin = MillisToMin.convertToString(startTimeMillis);
            String endHour = MillisToHour.convertToString(endTimeMillis);
            String endMin = MillisToMin.convertToString(endTimeMillis);


            Log.d(TAG, "onBindViewHolder: " + studyStartTime + " of " + noteList.get(holder.getAdapterPosition()).getSubject());



///it is for the status
            if (curTimeMillis > endTimeMillis) {
                ((CurrDayHomeTimeTableViewHolder) holder).txtStatus.setText("Finished");
            } else {
                if (curTimeMillis < startTimeMillis) {
                    ((CurrDayHomeTimeTableViewHolder) holder).txtStatus.setText("Upcoming");

                } else {
                    ((CurrDayHomeTimeTableViewHolder) holder).txtStatus.setText("");

                }
            }

/// this is for the color of todays live task
            if (curTimeMillis >= startTimeMillis && curTimeMillis <= endTimeMillis) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((CurrDayHomeTimeTableViewHolder) holder).layoutPrimary.getBackground().setTint(Color.parseColor("#b1fcc1"));
                }
                ((CurrDayHomeTimeTableViewHolder) holder).layoutPrimary.getBackground().setLevel(100 * 100);
            } else {
                ((CurrDayHomeTimeTableViewHolder) holder).layoutPrimary.getBackground().setLevel(0);

            }









            if (studyStartTime == -1 && curTimeMillis > endTimeMillis) {
                Log.d(TAG, "onBindViewHolder: " + noteList.get(holder.getAdapterPosition()).getSubject() + " " + noteList.get(holder.getAdapterPosition()).getEndStartTime());
                ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setVisibility(View.GONE);
                ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText("Missed !!!");
            }else if(studyStartTime != -1)
            {
                ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setVisibility(View.VISIBLE);

                String hrS = MillisToHour.convertToString(studyStartTime);
                String minS = MillisToMin.convertToString(studyStartTime);
                ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setText(hrS + "." + minS);

                if(studyEndTime==-1 && curTimeMillis >= endTimeMillis)
                {
                    String hrE = MillisToHour.convertToString(endTimeMillis);
                    String minE = MillisToMin.convertToString(endTimeMillis);
                    ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText(hrE + "." + minE);



                }
                else if(studyEndTime != -1)
                {
                    String hr = MillisToHour.convertToString(studyEndTime);
                    String min = MillisToMin.convertToString(studyEndTime);
                    ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText(hr + "." + min);

                }
                else{
                    ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText("End");

                }



            }

            else {
                ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setVisibility(View.VISIBLE);
                ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setText("Start");

                ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText("End");
            }


/// start from here
            String haveHad = "have";

            if(curTimeMillis>endTimeMillis || studyEndTime!=-1 ||!((CurrDayHomeTimeTableViewHolder)holder).txtEnd.getText().equals("End"))
            {
                ((CurrDayHomeTimeTableViewHolder) holder).txtAddBreak.setClickable(false);
                haveHad = "had";
            }



//            if (!isValidTime(startTimeMillis, endTimeMillis)) {
//
//
//            }

            int breakAmnt = noteList.get(holder.getAdapterPosition()).getBreakAmt();

            if (breakAmnt == 0 ) {
                if(haveHad.equals("had"))
                {
                    ((CurrDayHomeTimeTableViewHolder) holder).txtAddBreak.setText("No breaks were added");

                }
                else
                ((CurrDayHomeTimeTableViewHolder) holder).txtAddBreak.setText("Add a break");
            } else
                ((CurrDayHomeTimeTableViewHolder) holder).txtAddBreak.setText("You " + "have" + " added " + noteList.get(holder.getAdapterPosition()).getBreakAmt() + " min(s) of break");

            ((CurrDayHomeTimeTableViewHolder) holder).imgCategory.setImageResource(Constants.categoryDrawables[noteList.get(holder.getAdapterPosition()).getCategory() - 1]);

            ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<noteList.size())
                    {
                        Log.d(TAG, "onClick: the end end time is " + noteList.get(holder.getAdapterPosition()).getEndEndTime());


                        if ((!((CurrDayHomeTimeTableViewHolder) holder).txtStart.getText().equals("Start")) && ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.getText().equals("End")) {
                            Constants.currentTime24Form();
                            int currTimeMillis = Constants.hr24 * 60 + Constants.min;
                            if (currTimeMillis >= noteList.get(holder.getAdapterPosition()).getSetEndTime() && noteList.get(holder.getAdapterPosition()).getEndEndTime()==-1) {
                                String hr = MillisToHour.convertToString(noteList.get(holder.getAdapterPosition()).getSetEndTime());
                                String min = MillisToMin.convertToString(noteList.get(holder.getAdapterPosition()).getSetEndTime());
                                ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText(hr + "." + min);
                            }
                            else{
                                if(currTimeMillis <= noteList.get(holder.getAdapterPosition()).getSetEndTime())
                                {
                                    String hr = MillisToHour.convertToString(currTimeMillis);
                                    String min = MillisToMin.convertToString(currTimeMillis);
                                    ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText(hr + "." + min);

                                    startEndClickListener.onStartEndClickListener('e', currTimeMillis, "", holder.getAdapterPosition());
                                }
                            }

//                        if (((noteList.get(holder.getAdapterPosition()).getEndStartTime() != -1 || !((CurrDayHomeTimeTableViewHolder) holder).txtStart.getText().equals("Start")) && noteList.get(holder.getAdapterPosition()).getEndEndTime() == -1)) {
//
//
//                            if (currTimeMillis < noteList.get(holder.getAdapterPosition()).getSetEndTime()) {
//
//
//                                String hr = MillisToHour.convertToString(currTimeMillis);
//                                String min = MillisToMin.convertToString(currTimeMillis);
//                                ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText(hr + "." + min);
//
//                                startEndClickListener.onStartEndClickListener('e', currTimeMillis, "", holder.getAdapterPosition());
//                            }
//                        }
                        }


                    }
                }
            });

            ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  //  Log.d(TAG, "onClick: " + "click registered for outer " + noteList.get(holder.getAdapterPosition()).getSubject() + " value of endStartTime is " + studyStartTime + " the real is " + noteList.get(holder.getAdapterPosition()).getEndStartTime());

                   if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<noteList.size())
                   {
                       Constants.currentTime24Form();
                       if (noteList.get(holder.getAdapterPosition()).getSetEndTime() < (Constants.hr24 * 60 + Constants.min) && !(noteList.get(holder.getAdapterPosition()).getEndStartTime() != -1 || !((CurrDayHomeTimeTableViewHolder) holder).txtStart.getText().equals("Start"))) {


                           ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setVisibility(View.GONE);
                           ((CurrDayHomeTimeTableViewHolder) holder).txtEnd.setText("Missed !!!");

                       } else if (noteList.get(holder.getAdapterPosition()).getEndStartTime() == -1) {
                           Log.d(TAG, "onClick: " + "click registered for " + noteList.get(holder.getAdapterPosition()).getSubject());
                           Constants.currentTime24Form();

                           int hr = Constants.hr24;
                           int min = Constants.min;

                           int millis = hr * 60 + min;
                           String h = MillisToHour.convertToString(millis);
                           String m = MillisToMin.convertToString(millis);
                           String msg = "";
                           if (noteList.get(holder.getAdapterPosition()).getSetStartTime() > millis) {
                               msg = "This slot is yet to begin";
                           } else
                               ((CurrDayHomeTimeTableViewHolder) holder).txtStart.setText(h + "." + m);
                           startEndClickListener.onStartEndClickListener('s', millis, msg, holder.getAdapterPosition());
                       }
                   }
                }
            });


            int estTimeMillis = 0;


            if (startTimeMillis > endTimeMillis) {
                estTimeMillis = 24 * 60 - startTimeMillis + endTimeMillis;
            } else {
                estTimeMillis = endTimeMillis - startTimeMillis;
            }
            String hr1 = MillisToHour.convertToString(estTimeMillis);
            String min1 = MillisToMin.convertToString(estTimeMillis);


            ((CurrDayHomeTimeTableViewHolder) holder).txtEstTime.setText("Total duration - " + hr1 + " hr " + min1 + " min");


            ((CurrDayHomeTimeTableViewHolder) holder).txtSetTime.setText(startHour + "." + startMin + " - " + endHour + "." + endMin);

            ((CurrDayHomeTimeTableViewHolder) holder).txtSubject.setText(noteList.get(holder.getAdapterPosition()).getSubject());


            ((CurrDayHomeTimeTableViewHolder) holder).txtNotes.setText(noteList.get(holder.getAdapterPosition()).getDescription());

            if (noteList.get(holder.getAdapterPosition()).isClicked()) {
                ((CurrDayHomeTimeTableViewHolder) holder).layoutExpand.setVisibility(View.VISIBLE);

            } else {
                ((CurrDayHomeTimeTableViewHolder) holder).layoutExpand.setVisibility(View.GONE);

            }


            if (position % 2 == 0)
                ((CurrDayHomeTimeTableViewHolder) holder).colorLayout.setBackgroundResource(R.drawable.gradient_green);
            else
                ((CurrDayHomeTimeTableViewHolder) holder).colorLayout.setBackgroundResource(R.drawable.gradient_pink);


            ((CurrDayHomeTimeTableViewHolder) holder).btnAddBreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<noteList.size())
                    {
                        String s = ((CurrDayHomeTimeTableViewHolder) holder).edtBreak.getText().toString();

                        if (s != null && s.length() > 0) {
                            int amnt = Integer.parseInt(s);
                            noteList.get(holder.getAdapterPosition()).setBreakClicked(false);
                            ((CurrDayHomeTimeTableViewHolder) holder).layoutAddBreak.setVisibility(View.GONE);

                            addBreakClickListener.onAddBreakClickListener(holder.getAdapterPosition(), amnt, ((CurrDayHomeTimeTableViewHolder) holder).txtAddBreak, noteList.get(holder.getAdapterPosition()).getSetStartTime(), noteList.get(holder.getAdapterPosition()).getSetEndTime());


                        }
                    }
                }
            });

            ((CurrDayHomeTimeTableViewHolder) holder).btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<noteList.size())
                    {
                        noteList.get(holder.getAdapterPosition()).setClicked(false);
                        ((CurrDayHomeTimeTableViewHolder) holder).layoutAddBreak.setVisibility(View.GONE);
                    }


                }
            });

            finalHaveHad = haveHad;
            ((CurrDayHomeTimeTableViewHolder) holder).txtAddBreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<noteList.size())
                   {
                       Constants.currentTime24Form();
                       int x = Constants.hr24 * 60 + Constants.min;


                       ((CurrDayHomeTimeTableViewHolder) holder).layoutAddBreak.setVisibility(View.VISIBLE);
                       noteList.get(holder.getAdapterPosition()).setBreakClicked(true);

                   }
                }
            });


            ((CurrDayHomeTimeTableViewHolder) holder).topLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<noteList.size())
                    {
                        if (noteList.get(holder.getAdapterPosition()).isClicked()) {
                            noteList.get(holder.getAdapterPosition()).setClicked(false);
                            ((CurrDayHomeTimeTableViewHolder) holder).layoutExpand.setVisibility(View.GONE);
                        } else {
                            noteList.get(holder.getAdapterPosition()).setClicked(true);
                            ((CurrDayHomeTimeTableViewHolder) holder).layoutExpand.setVisibility(View.VISIBLE);
                        }
                        Log.d(TAG, "onClick: ");
                    }


                }
            });


        }
    }


    public boolean isValidTime(int startTime, int endTime) {
        Constants.currentTime24Form();

        int curTimeMillis = Constants.hr24 * 60 + Constants.min;
        if ((startTime < curTimeMillis && endTime < curTimeMillis && startTime < endTime))
            return false;


        return true;
    }

    public void setAddBreakClickListener(OnAddBreakClickListener listener) {
        addBreakClickListener = listener;

    }

    public void setStartEndListener(OnStartEndClickListener listener) {
        startEndClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    public int getItemViewType(int position) {

       if(noteList.get(position).getDay().equals("day"))
       {
           turn = 2;
           return 2;
       }
       else{
           turn = 1;
           return 1;
       }

    }

    public void setNoteList(List<Note> list) {
        noteList = list;
        notifyDataSetChanged();
    }

}
