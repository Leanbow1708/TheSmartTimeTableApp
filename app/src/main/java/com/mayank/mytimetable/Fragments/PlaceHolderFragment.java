package com.mayank.mytimetable.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mayank.mytimetable.R;

public class PlaceHolderFragment extends Fragment {


    ShimmerFrameLayout shimmerFrameLayout;

    public PlaceHolderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shimmerFrameLayout = getActivity().findViewById(R.id.shimmer_view_container);
    }

    @Override
    public void onStart() {
        super.onStart();

        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_holder, container, false);
    }
}