package com.mayank.mytimetable.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mayank.mytimetable.Adapters.AboutUsAdapter;
import com.mayank.mytimetable.MainActivity;
import com.mayank.mytimetable.R;

public class AboutUsFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "AboutUsFragment";

    RecyclerView recyclerView;
    ConstraintLayout layout_1;
    ConstraintLayout layout_0;
    ConstraintLayout layout_2;
    MotionLayout layoutMotion;
    View viewHelp, viewAbout, viewUpcoming;
    ImageView imgHelp, imgAbout, imgUpcoming, imgGmail, imgInstagram, imgLinkedin, imgGithub;
    TextView txtHelp, txtAbout, txtUpcoming;
    AboutUsAdapter adapter;

    NestedScrollView layoutAboutDeveloper;
    TextView txtHeader;


    int turn = 1;
    boolean isOpen = false;


    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView = getActivity().findViewById(R.id.recycler_about_us);
        layoutMotion = getActivity().findViewById(R.id.motion_layout_about);
        viewHelp = getActivity().findViewById(R.id.view2);
        viewAbout = getActivity().findViewById(R.id.view_about);
        viewUpcoming = getActivity().findViewById(R.id.view_upcoming);
        imgHelp = getActivity().findViewById(R.id.neumorphImageView);
        imgAbout = getActivity().findViewById(R.id.img_about);
        imgUpcoming = getActivity().findViewById(R.id.img_upcoming);
        imgGmail = getActivity().findViewById(R.id.img_gmail);
        imgInstagram = getActivity().findViewById(R.id.img_instagram);
        imgLinkedin = getActivity().findViewById(R.id.img_linkedn);
        imgGithub = getActivity().findViewById(R.id.img_github);
        txtHelp = getActivity().findViewById(R.id.txtHelp);
        txtUpcoming = getActivity().findViewById(R.id.txt_upcoming);
        txtAbout = getActivity().findViewById(R.id.textView3);
    }


    @Override
    public void onStart() {
        super.onStart();

        if (MainActivity.shimmerAboutUs != null) {
            MainActivity.shimmerAboutUs.stopShimmerAnimation();

            MainActivity.shimmerAboutUs.setVisibility(View.GONE);
        }


        adapter = new AboutUsAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setTurn(1);


        imgGithub.setOnClickListener(this);
        imgLinkedin.setOnClickListener(this);
        imgInstagram.setOnClickListener(this);
        imgGmail.setOnClickListener(this);
        imgUpcoming.setOnClickListener(this);
        imgAbout.setOnClickListener(this);
        imgHelp.setOnClickListener(this);

    }

    void refreshOnOpen() {
        Log.d(TAG, "refreshOnOpen: refreshed");


        viewUpcoming.setVisibility(View.INVISIBLE);
        viewAbout.setVisibility(View.INVISIBLE);
        viewHelp.setVisibility(View.INVISIBLE);
        txtHelp.setTextColor(Color.parseColor("#828282"));
        txtAbout.setTextColor(Color.parseColor("#828282"));
        txtUpcoming.setTextColor(Color.parseColor("#828282"));
        imgHelp.setImageResource(R.drawable.ic_help);
        imgAbout.setImageResource(R.drawable.ic_person_outlined);
        imgUpcoming.setImageResource(R.drawable.ic_upcoming);
    }


    @Override
    public void onClick(View view) {

        viewUpcoming.setBackgroundColor(Color.parseColor("#e9e3fa"));
        viewHelp.setBackgroundColor(Color.parseColor("#e9e3fa"));
        viewAbout.setBackgroundColor(Color.parseColor("#e9e3fa"));

        switch (view.getId()) {

            case R.id.img_github: {
                Uri uri = Uri.parse("https://github.com/Leanbow1708");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                try {

                    getActivity().startActivity(i);
                } catch (Exception e) {
                }
                break;
            }


            case R.id.img_instagram: {
                Uri uri = Uri.parse("https://www.instagram.com/leanbow__");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                try {

                    getActivity().startActivity(i);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + "instagram not found");
                }
                break;


            }


            case R.id.img_linkedn: {
                Uri uri = Uri.parse("https://www.linkedin.com/in/mayank-singh-1004981a4/");


                Intent i = new Intent(Intent.ACTION_VIEW, uri);


                try {
                    getActivity().startActivity(i);
                } catch (ActivityNotFoundException e) {

                }
                break;
            }


            case R.id.img_gmail: {
                try {

                    Intent email = new Intent(Intent.ACTION_SENDTO);
                    email.setPackage("com.google.android.gm");
                    email.setData(Uri.parse("mailto:mayankcodes1@gmail.com"));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    email.putExtra(Intent.EXTRA_TEXT, "");

                    getActivity().startActivity(email);


                } catch (Exception e) {

                }
                break;
            }


            case R.id.neumorphImageView: {
                checkForOpenOrClose(0);
                if (isOpen) {
                    Log.d(TAG, "onClick: help clickde");
                    refreshOnOpen();
                    viewHelp.setVisibility(View.VISIBLE);
                    viewHelp.setBackgroundColor(Color.parseColor("#9a84db"));
                    imgHelp.setImageResource(R.drawable.ic_help_primary);
                    txtHelp.setTextColor(Color.parseColor("#9a84db"));
                }
                break;
            }

            case R.id.img_about: {


//                viewHelp.setVisibility(View.INVISIBLE);

                checkForOpenOrClose(1);
                if (isOpen) {

                    Log.d(TAG, "onClick: about clicked");
                    refreshOnOpen();
                    viewAbout.setVisibility(View.VISIBLE);
                    viewAbout.setBackgroundColor(Color.parseColor("#9a84db"));
                    imgAbout.setImageResource(R.drawable.ic_about_primary);
                    txtAbout.setTextColor(Color.parseColor("#9a84db"));
                }
                break;
            }
            case R.id.img_upcoming:
                checkForOpenOrClose(2);
                if (isOpen) {

                    Log.d(TAG, "onClick: about clicked");
                    refreshOnOpen();
                    viewUpcoming.setVisibility(View.VISIBLE);
                    viewUpcoming.setBackgroundColor(Color.parseColor("#9a84db"));
                    imgUpcoming.setImageResource(R.drawable.ic_upcoming_primary);
                    txtUpcoming.setTextColor(Color.parseColor("#9a84db"));
                }
                break;


        }


    }

    private void checkForOpenOrClose(int i) {


        if (turn != i) {

            refreshViews(i);

            turn = i;
            if (!isOpen) {
                Log.d(TAG, "checkForOpenOrClose: transition to end");
                isOpen = true;
                layoutMotion.transitionToEnd();
            }
        } else {
            if (isOpen) {
                Log.d(TAG, "checkForOpenOrClose: transitiotnto start");

                isOpen = false;
                layoutMotion.transitionToStart();

            } else {
                Log.d(TAG, "checkForOpenOrClose: transition to end");
                isOpen = true;
                layoutMotion.transitionToEnd();
            }
        }

    }

    private void refreshViews(int i) {


        switch (i) {
            case 0:
                adapter.setTurn(0);
                break;
            case 1:
                adapter.setTurn(1);
                break;
            case 2:
                adapter.setTurn(2);
                break;
        }


    }
}