package com.mayank.mytimetable.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mayank.mytimetable.R;

public class CurrDayHomeTimeTableViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout colorLayout;
    public ImageView imgExpand;
    public ConstraintLayout layoutExpand;
    public TextView txtSubject;
    public TextView txtEstTime;
    public TextView txtSetTime;
    public TextView txtNotes;
    public ImageView imgEdit;
    public ImageView imgCategory;
    public MaterialCardView topLayout;
    public TextView txtAddBreak;
    public EditText edtBreak;
    public Button btnAddBreak;
    public LinearLayout layoutAddBreak;
    public TextView txtStart;
    public TextView txtEnd;
    public Button btnClose;
    public ConstraintLayout layoutPrimary;
    public TextView txtStatus;


    public CurrDayHomeTimeTableViewHolder(@NonNull View itemView) {
        super(itemView);

        txtStatus = itemView.findViewById(R.id.txt_status);
        layoutPrimary = itemView.findViewById(R.id.layout_primary);
        btnClose = itemView.findViewById(R.id.btn_close);
        txtStart = itemView.findViewById(R.id.txt_start);
        txtEnd = itemView.findViewById(R.id.txt_end);
        edtBreak = itemView.findViewById(R.id.edt_time_min);
        btnAddBreak = itemView.findViewById(R.id.btn_add_break);
        layoutAddBreak = itemView.findViewById(R.id.layout_break);
        colorLayout = itemView.findViewById(R.id.color_layout);
        imgExpand = itemView.findViewById(R.id.img_expand);
        layoutExpand = itemView.findViewById(R.id.layout_expand);
        txtNotes = itemView.findViewById(R.id.txt_notes);
        txtSubject = itemView.findViewById(R.id.txt_subject);
        txtEstTime = itemView.findViewById(R.id.txt_est_time);
        txtSetTime = itemView.findViewById(R.id.textView);
        imgEdit = itemView.findViewById(R.id.img_edit);
        imgCategory = itemView.findViewById(R.id.imageView);
        topLayout = itemView.findViewById(R.id.motion_layout);
        txtAddBreak = itemView.findViewById(R.id.txt_add_break);

        edtBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtBreak.requestFocus(View.FOCUS_DOWN))
                {
                    throw new IllegalStateException(
                            "focus search returned a view " +
                                    "that wasn't able to take focus!");
                }
            }
        });
       
    }
}
