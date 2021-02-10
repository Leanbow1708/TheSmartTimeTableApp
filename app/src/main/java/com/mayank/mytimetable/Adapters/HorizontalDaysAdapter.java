package com.mayank.mytimetable.Adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.DataClass.Days;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Utils.OnDayClickListener;

import java.util.ArrayList;
import java.util.List;

public class HorizontalDaysAdapter extends RecyclerView.Adapter<HorizontalDaysAdapter.MyHolder>{


    private List<Days> daysList = new ArrayList<>();
    private OnDayClickListener mOnDayClickListener;

    public HorizontalDaysAdapter() {

    }

    public void setDaysList(List<Days> list)
    {
        daysList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_horizontal_day, parent, false);
       return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


//        holder.txtDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        holder.txtDay.setText(daysList.get(position).getDay());
        if(daysList.get(position).isCLicked)
        {
            Log.d("Adapter", "onBindViewHolder: "+"clcik");
            holder.txtDay.setBackgroundResource(R.drawable.btn_days_grey);
            holder.txtDay.setTextColor(Color.parseColor("#ffffff"));

        }
        else{
            holder.txtDay.setBackgroundResource(R.drawable.btn_days_not_selected);
            holder.txtDay.setTextColor(Color.parseColor("#000000"));

        }


    }

    @Override
    public int getItemCount() {
        if(daysList == null)
            return 0;
        else
        return daysList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView txtDay;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txtDay = itemView.findViewById(R.id.txt_day);
            txtDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mOnDayClickListener.onDayClick(getAdapterPosition());



                }
            });

        }
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener){
        mOnDayClickListener = onDayClickListener;

    }

}
