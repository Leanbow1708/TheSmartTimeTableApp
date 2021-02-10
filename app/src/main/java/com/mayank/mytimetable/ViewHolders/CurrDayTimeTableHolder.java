package com.mayank.mytimetable.ViewHolders;

import android.media.Image;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.R;

public class CurrDayTimeTableHolder extends RecyclerView.ViewHolder {
    public LinearLayout colorLayout;
    public ImageView imgExpand;
    public ConstraintLayout layoutExpand;
    public TextView txtSubject;
    public TextView txtEstTime;
    public TextView txtSetTime;
    public TextView txtNotes;
    public ImageView imgEdit;
    public ImageView imgCategory;
    public ImageView imgDelete;
    public CurrDayTimeTableHolder(@NonNull View itemView) {
        super(itemView);
        imgDelete = itemView.findViewById(R.id.img_delete);
        colorLayout = itemView.findViewById(R.id.color_layout);
        imgExpand = itemView.findViewById(R.id.img_expand);
        layoutExpand = itemView.findViewById(R.id.layout_expand);
        txtNotes = itemView.findViewById(R.id.txt_notes);
        txtSubject = itemView.findViewById(R.id.txt_subject);
        txtEstTime = itemView.findViewById(R.id.txt_est_time);
        txtSetTime = itemView.findViewById(R.id.textView);
        imgEdit = itemView.findViewById(R.id.img_edit);
        imgCategory = itemView.findViewById(R.id.imageView);





    }
}
