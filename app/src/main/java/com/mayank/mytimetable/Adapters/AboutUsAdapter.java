package com.mayank.mytimetable.Adapters;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mayank.mytimetable.R;
import com.mayank.mytimetable.ViewHolders.AboutUsHelpViewHolder;
import com.mayank.mytimetable.ViewHolders.FeaturesViewHolder;
import com.mayank.mytimetable.ViewHolders.HelpViewHolder;

public class AboutUsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int turn = 1;

    private static final String TAG = "AboutUsAdapter";
   public void setTurn(int turn)
   {
       this.turn = turn;
       notifyDataSetChanged();
   }

   Activity activity;

    public AboutUsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


       View view;
       switch (turn)
       {
           case 0:
               view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_about_us_0, parent, false);
                return new HelpViewHolder(view);
           case 1:
               view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_about_us_1, parent, false);
                return new AboutUsHelpViewHolder(view);
           case 2:
               view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_about_us_2, parent, false);
                    return new FeaturesViewHolder(view);


       }


        return null;
    }

    @Override
    public int getItemViewType(int position) {
       return  turn;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       getItemViewType(holder.getAdapterPosition());

       if(turn == 1)
       {

           ((AboutUsHelpViewHolder)holder).imgInstagram.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
             Uri uri = Uri.parse("https://www.instagram.com/leanbow__");
                 Intent i= new Intent(Intent.ACTION_VIEW,uri);
                     try {

                       activity.startActivity(i);
                   } catch (Exception  e) {
                       Log.d(TAG, "onClick: "+"instagram not found");
                   }


               }
           });

           ((AboutUsHelpViewHolder)holder).imgLinkedin.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Uri uri = Uri.parse("https://www.linkedin.com/in/mayank-singh-1004981a4/");


                   Intent i= new Intent(Intent.ACTION_VIEW,uri);



                   try {
                       activity.startActivity(i);
                   } catch (ActivityNotFoundException e) {

                   }
               }
           });

           ((AboutUsHelpViewHolder)holder).imgGmail.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 try {

                     Intent email= new Intent(Intent.ACTION_SENDTO);
                     email.setPackage("com.google.android.gm");
                     email.setData(Uri.parse("mailto:mayankcodes1@gmail.com"));
                     email.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                     email.putExtra(Intent.EXTRA_TEXT, "");

                     activity.startActivity(email);


                 }
                 catch (Exception e)
                 {

                 }
               }
           });




       }


    }


    @Override
    public int getItemCount() {
        return 1;
    }
}
