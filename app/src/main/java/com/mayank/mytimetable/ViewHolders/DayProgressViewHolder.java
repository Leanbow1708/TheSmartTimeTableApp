package com.mayank.mytimetable.ViewHolders;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class DayProgressViewHolder extends RecyclerView.ViewHolder {

    public TextView txtTotalDuration;
    public TextView txtTimeSlot;
    public TextView txtPercentProgress;
    public CircularProgressBar progressBar;
    public TextView txtSubject;
    public TextView txtMissed;
    public ConstraintLayout layoutPrimary;
    public ImageView imgChart;

    public DayProgressViewHolder(@NonNull View itemView) {
        super(itemView);


        txtPercentProgress = itemView.findViewById(R.id.txt_percent_progress);
        txtTotalDuration = itemView.findViewById(R.id.txt_est_time);
        txtTimeSlot = itemView.findViewById(R.id.textView);
        progressBar = itemView.findViewById(R.id.progressBar);
        txtSubject = itemView.findViewById(R.id.txt_subject);
        txtMissed = itemView.findViewById(R.id.txt_missed);
        layoutPrimary = itemView.findViewById(R.id.motion_layout);
        imgChart = itemView.findViewById(R.id.img_chart);

    }
}
