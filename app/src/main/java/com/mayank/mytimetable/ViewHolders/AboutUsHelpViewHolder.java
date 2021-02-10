package com.mayank.mytimetable.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.R;

public class AboutUsHelpViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgInstagram,imgLinkedin,imgGmail;

    public AboutUsHelpViewHolder(@NonNull View itemView) {
        super(itemView);

        imgGmail = itemView.findViewById(R.id.img_gmail_1);
        imgInstagram = itemView.findViewById(R.id.img_instagram_1);
        imgLinkedin = itemView.findViewById(R.id.img_linkedn_1);


    }
}
