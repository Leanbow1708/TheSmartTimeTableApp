package com.mayank.mytimetable.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Utils.Constants;
import com.mayank.mytimetable.Utils.MillisToHour;
import com.mayank.mytimetable.Utils.MillisToMin;
import com.mayank.mytimetable.Utils.OnAlreadySetDayTTClickListener;
import com.mayank.mytimetable.Utils.OnDeleteClickListener;
import com.mayank.mytimetable.Utils.OnEditClickListener;
import com.mayank.mytimetable.ViewHolders.CurrDayTimeTableHolder;
import com.mayank.mytimetable.ViewHolders.NoDataFoundViewHolder;
import com.mayank.mytimetable.ViewHolders.PrevDaysViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnEditClickListener mOnEditClickListener;
    public static int tt = 1;
    private OnDeleteClickListener onDeleteClickListener;
    private static final String TAG = "MainAdapter";
    List<Note> list = new ArrayList<>();

    OnAlreadySetDayTTClickListener onAlreadySetDayTTClickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;


        switch (tt)
        {
            case 2:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nodata_found, parent,false );

                return new NoDataFoundViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_table_slot, parent,false );
                return new CurrDayTimeTableHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prev_days_set_tt, parent, false);
                return new PrevDaysViewHolder(view);
        }

//       if(tt == 1)
//       {
//           View view;
//           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_table_slot, parent,false );
//           return new CurrDayTimeTableHolder(view);
//       }
//       else if(tt == 2){
//           View view;
//           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nodata_found, parent,false );
//
//           return new NoDataFoundViewHolder(view);
//
//       }
//       else{
//           View view;
//           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_table_slot, parent,false );
//           return new CurrDayTimeTableHolder(view);
//       }
//        //return new CurrDayTimeTableHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        getItemViewType(position);

        if(tt == 1){


            ((CurrDayTimeTableHolder)holder).imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onDeleteClickListener.onDeleteClickListener(holder.getAdapterPosition(),list.size()-1);

                    list.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                }
            });

            ((CurrDayTimeTableHolder)holder).imgCategory.setImageResource(Constants.categoryDrawables[list.get(holder.getAdapterPosition()).getCategory()-1]);
            ((CurrDayTimeTableHolder)holder).imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnEditClickListener.onEditClick(list.get(position));
                }
            });

            int startTimeMillis = list.get(holder.getAdapterPosition()).getSetStartTime();
            String startHour = MillisToHour.convertToString(startTimeMillis);
            String startMin = MillisToMin.convertToString(startTimeMillis);


            int endTimeMillis = list.get(holder.getAdapterPosition()).getSetEndTime();
            String endHour = MillisToHour.convertToString(endTimeMillis);
            String endMin = MillisToMin.convertToString(endTimeMillis);



            int estTimeMillis = 0;



            if(startTimeMillis > endTimeMillis)
            {
                estTimeMillis = 24*60 - startTimeMillis + endTimeMillis;
            }
            else{
                estTimeMillis = endTimeMillis - startTimeMillis;
            }
            String hr1 = MillisToHour.convertToString(estTimeMillis);
            String min1 = MillisToMin.convertToString(estTimeMillis);






            ((CurrDayTimeTableHolder)holder).txtEstTime.setText("Total duration - "+hr1+" hr "+min1+" min");


            ((CurrDayTimeTableHolder)holder).txtSetTime.setText(startHour+"."+startMin+" - "+endHour+"."+endMin);

            ((CurrDayTimeTableHolder)holder).txtSubject.setText(list.get(holder.getAdapterPosition()).getSubject());


            ((CurrDayTimeTableHolder)holder).txtNotes.setText(list.get(holder.getAdapterPosition()).getDescription());

            if(list.get(holder.getAdapterPosition()).isClicked())
            {
                ((CurrDayTimeTableHolder)holder).layoutExpand.setVisibility(View.VISIBLE);

            }
            else{
                ((CurrDayTimeTableHolder)holder).layoutExpand.setVisibility(View.GONE);

            }


            if(position % 2 == 0)
                ((CurrDayTimeTableHolder)holder).colorLayout.setBackgroundResource(R.drawable.gradient_green);
            else
                ((CurrDayTimeTableHolder)holder).colorLayout.setBackgroundResource(R.drawable.gradient_pink);

            ((CurrDayTimeTableHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(holder.getAdapterPosition()>=0 && holder.getAdapterPosition()<list.size())
                    {
                        if(list.get(holder.getAdapterPosition()).isClicked())
                        {
                            list.get(holder.getAdapterPosition()).setClicked(false);
                            ((CurrDayTimeTableHolder)holder).layoutExpand.setVisibility(View.GONE);
                        }
                        else{
                            list.get(holder.getAdapterPosition()).setClicked(true);
                            ((CurrDayTimeTableHolder)holder).layoutExpand.setVisibility(View.VISIBLE);
                        }
                        Log.d(TAG, "onClick: ");


                    }










                }
            });





        }
        else if(tt == 3)
        {
            ((PrevDaysViewHolder)holder).btnDays.setText(Constants.dayOfWeek2[list.get(position).getCategory()]);
            ((PrevDaysViewHolder)holder).btnDays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAlreadySetDayTTClickListener.OnClickListener(holder.getAdapterPosition());
                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getDay().equals("day"))
        {
            tt = 2;
            return 2;
        }
        else if(list.size()>1 && list.get(position).getDay().equals("set"))
        {
            tt = 3;
            return 3;
        }
        else{
            tt = 1;
            return 1;

        }


    }

    public void setList(List<Note> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnDeleteClickListener(OnDeleteClickListener deleteClickListener){
        onDeleteClickListener = deleteClickListener;
    }

    public void setOnEditClickListener(OnEditClickListener editClickListener)
    {
        mOnEditClickListener = editClickListener;
    }

    public void setOnAlreadySetDayTTClickListener(OnAlreadySetDayTTClickListener a)
    {
        onAlreadySetDayTTClickListener = a;
    }
}
