package com.mayank.mytimetable.ViewHolders;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Utils.OnAlreadySetDayTTClickListener;

public class PrevDaysViewHolder extends RecyclerView.ViewHolder {

 public   Button btnDays;
    public PrevDaysViewHolder(@NonNull View itemView) {
        super(itemView);
        btnDays = itemView.findViewById(R.id.btn_day);



    }
}
