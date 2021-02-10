package com.mayank.mytimetable.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mayank.mytimetable.Adapters.HorizontalDaysAdapter;
import com.mayank.mytimetable.Adapters.ProgressDayAdapter;
import com.mayank.mytimetable.DataClass.Days;
import com.mayank.mytimetable.DataClass.SavedNotes;
import com.mayank.mytimetable.MainActivity;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Utils.Constants;
import com.mayank.mytimetable.Utils.MillisToHour;
import com.mayank.mytimetable.Utils.MillisToMin;
import com.mayank.mytimetable.Utils.OnChartClickListener;
import com.mayank.mytimetable.Utils.OnDayClickListener;
import com.mayank.mytimetable.ViewModel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AllDayProgressFragment extends Fragment {

    ArrayList<Days> daysList = new ArrayList<>();
    RecyclerView recyclerDay;
    HorizontalDaysAdapter daysAdapter;
    private RecyclerView recyclerProgress;
    private static final String TAG = "AllDayProgressFragment";
    int todayDay = 0;
    NoteViewModel noteViewModel;
    List<SavedNotes> savedNotesList = new ArrayList<>();
    ProgressDayAdapter progressDayAdapter;
    PieChart pieChart;
    TextView txtTotal;
    TextView txtUsed;
    TextView txtUnused;
    MotionLayout motionLayout;
    Button btnClose;
    TextView txtDay;
    Typeface custom_font;
    ImageView imgAllProgress;
    int totalDuration = 0;
    int usedDuration = 0;
    int unusedDuration = 0;
    int totalBreakTaken = 0;


    public AllDayProgressFragment() {
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

        return inflater.inflate(R.layout.fragment_all_day_progress, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        imgAllProgress = getActivity().findViewById(R.id.img_all_progress);
        txtDay = getActivity().findViewById(R.id.textView2);
        recyclerDay = getActivity().findViewById(R.id.recycler_day);
        daysAdapter = new HorizontalDaysAdapter();


        pieChart = getActivity().findViewById(R.id.pie_chart);
        txtTotal = getActivity().findViewById(R.id.txt_total);
        txtUsed = getActivity().findViewById(R.id.txt_used);
        txtUnused = getActivity().findViewById(R.id.txt_unused);
        motionLayout = getActivity().findViewById(R.id.motion_layout_progress);

        recyclerProgress = getActivity().findViewById(R.id.recycler_progress);
        progressDayAdapter = new ProgressDayAdapter();
        btnClose = getActivity().findViewById(R.id.btn_close);

    }


    @Override
    public void onStart() {
        super.onStart();


        imgAllProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalDuration > 0)
                {

                    int realUsed = usedDuration - totalBreakTaken;
                    realUsed = Math.max(realUsed,0);
                    int realUnused = totalDuration - realUsed;


                    motionLayout.transitionToEnd();

                    btnClose.setVisibility(View.VISIBLE);
                    recyclerProgress.setAlpha(0.2f);
                    ArrayList<PieEntry> entries = new ArrayList<>();


                    float totalPer = (float)totalDuration;
                    float usedPer = (float)realUsed;
                    float unusedPer = (float)realUnused;

                    usedPer = (usedPer*100)/totalPer;
                    unusedPer = (unusedPer*100)/totalPer;

                    entries.add(new PieEntry(usedPer,"Used"));
                    entries.add(new PieEntry(unusedPer,"Unused"));
                    double roundOffUsed = Math.round(usedPer * 100.0) / 100.0;
                    double roundOffUnUsed = Math.round(unusedPer * 100.0) / 100.0;


                    txtTotal.setText(MillisToHour.convertToString(totalDuration)+" hr "+ MillisToMin.convertToString(totalDuration)+" min");
                    txtUsed.setText(MillisToHour.convertToString(realUsed)+" hr "+ MillisToMin.convertToString(realUsed)+" min"+"  "+roundOffUsed+"%");
                    txtUnused.setText(MillisToHour.convertToString(realUnused)+" hr "+ MillisToMin.convertToString(realUnused)+" min"+"  "+roundOffUnUsed+"%");

                    PieDataSet dataSet = new PieDataSet(entries, "Time Breakdown");

                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    PieData pieData = new PieData(dataSet);
                    pieData.setValueTextSize(15f);
                    pieData.setValueTypeface(custom_font);
                    pieChart.setData(pieData);
                    pieChart.invalidate();
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setHoleColor(Color.WHITE);
                    pieChart.getDescription().setEnabled(false);

                    pieChart.setTransparentCircleColor(Color.WHITE);
                    pieChart.setTransparentCircleAlpha(110);

                    pieChart.setHoleRadius(58f);
                    pieChart.setTransparentCircleRadius(61f);

                    pieChart.setDrawCenterText(true);
                    pieChart.animateXY(1000, 1000);


                    pieChart.setRotationAngle(0);
                    // enable rotation of the chart by touch
                    pieChart.setRotationEnabled(true);
                    pieChart.setHighlightPerTapEnabled(true);



                }



            }
        });

        if(MainActivity.shimmerFrameLayout != null)
        {
            MainActivity.shimmerFrameLayout.stopShimmerAnimation();
            MainActivity.shimmerFrameLayout.setVisibility(View.GONE);

        }


        custom_font = ResourcesCompat.getFont(getContext(), R.font.montserrat_light);

        recyclerDay.setAdapter(daysAdapter);
        recyclerDay.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        daysAdapter.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(int i) {

                if(i >= 0 && i < 7)
                {
                    txtDay.setText(daysList.get(i).getDay()+"'s  Progress");


                    for(int j = 0;j < 7;j++)
                    {
                        daysList.get(j).setCLicked(false);
                    }
                    daysList.get(i).setCLicked(true);
                    daysAdapter.setDaysList(daysList);
                    daysAdapter.notifyDataSetChanged();

                    progressDayAdapter.setCurDay(i);
                    progressDayAdapter.setNotesList(null);
                    noteViewModel.selectTheDaysProgress(Constants.dayOfWeek[i]);

                }





            }
        });

        getCurrentDayOfWeek();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                motionLayout.transitionToStart();
                btnClose.setVisibility(View.GONE);
                recyclerProgress.setAlpha(1f);

            }
        });

        recyclerProgress.setAdapter(progressDayAdapter);
        recyclerProgress.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerDay.getLayoutManager().scrollToPosition(todayDay-1);
        progressDayAdapter.setOnChartClickListener(new OnChartClickListener() {
            @Override
            public void onChartClickListener(int total, int used, int unused) {
                    if(total > 0)
                    {

                        used = Math.max(0,used);

                        motionLayout.transitionToEnd();

                        btnClose.setVisibility(View.VISIBLE);
                        recyclerProgress.setAlpha(0.2f);
                        ArrayList<PieEntry> entries = new ArrayList<>();


                        float totalPer = (float)total;
                        float usedPer = (float)used;
                        float unusedPer = (float)unused;

                        usedPer = (usedPer*100)/totalPer;
                        unusedPer = (unusedPer*100)/totalPer;

                        entries.add(new PieEntry(usedPer,"Used"));
                        entries.add(new PieEntry(unusedPer,"Unused"));
                        double roundOffUsed = Math.round(usedPer * 100.0) / 100.0;
                        double roundOffUnUsed = Math.round(unusedPer * 100.0) / 100.0;


                        txtTotal.setText(MillisToHour.convertToString(total)+" hr "+ MillisToMin.convertToString(total)+" min");
                        txtUsed.setText(MillisToHour.convertToString(used)+" hr "+ MillisToMin.convertToString(used)+" min"+"  "+roundOffUsed+"%");
                        txtUnused.setText(MillisToHour.convertToString(unused)+" hr "+ MillisToMin.convertToString(unused)+" min"+"  "+roundOffUnUsed+"%");

                        PieDataSet dataSet = new PieDataSet(entries, "Time Breakdown");

                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        PieData pieData = new PieData(dataSet);
                        pieData.setValueTextSize(15f);
                        pieData.setValueTypeface(custom_font);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.setDrawHoleEnabled(true);
                        pieChart.setHoleColor(Color.WHITE);
                        pieChart.getDescription().setEnabled(false);

                        pieChart.setTransparentCircleColor(Color.WHITE);
                        pieChart.setTransparentCircleAlpha(110);

                        pieChart.setHoleRadius(58f);
                        pieChart.setTransparentCircleRadius(61f);

                        pieChart.setDrawCenterText(true);
                        pieChart.animateXY(1000, 1000);


                        pieChart.setRotationAngle(0);
                        // enable rotation of the chart by touch
                        pieChart.setRotationEnabled(true);
                        pieChart.setHighlightPerTapEnabled(true);
                    }




            }
        });



    }

    private void fillDayList() {
        Days d1 = new Days("Sunday", false);
        Days d2 = new Days("Monday",false);
        Days d3 = new Days("Tuesday",false);
        Days d4 = new Days("Wednesday", false);
        Days d5 = new Days("Thursday",false);
        Days d6 = new Days("Friday",false);
        Days d7 = new Days("Saturday", false);

        daysList = new ArrayList<>();
        daysList.add(d1);
        daysList.add(d2);
        daysList.add(d3);
        daysList.add(d4);
        daysList.add(d5);
        daysList.add(d6);
        daysList.add(d7);
        daysList.get(todayDay-1).setCLicked(true);

        daysAdapter.setDaysList(daysList);

    }

    private void getCurrentDayOfWeek() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");



        String displayDate = dateFormat.format(date);

        //txt_curr_date.setText(displayDate);





        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        todayDay =  cal.get(Calendar.DAY_OF_WEEK);
        // daysList.get(Constants.day-1).setCLicked(true);

        Log.d(TAG, "getDayNumber: "+ Constants.day);

        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        attachObservers();
        noteViewModel.selectTheDaysProgress(Constants.dayOfWeek[todayDay-1]);

        progressDayAdapter.setCurDay(todayDay-1);
        fillDayList();
        txtDay.setText(daysList.get(todayDay-1).getDay()+"'s  Progress");

        // txt_curr_day.setText(dayOfWeek[Constants.day-1]);

    }

    private void attachObservers() {
        noteViewModel.getDayProgress().observe(getActivity(), new Observer<List<SavedNotes>>() {
            @Override
            public void onChanged(List<SavedNotes> list) {
                savedNotesList = new ArrayList<>();

                totalDuration = 0;
                usedDuration = 0;
                unusedDuration = 0;
                if(list!=null && list.size()>0)
                {
                    totalDuration = 0;
                    usedDuration = 0;
                    unusedDuration = 0;
                    progressDayAdapter.setNotesList(list);


                    savedNotesList = list;
                    for(SavedNotes n : list)
                    {

                        totalDuration = totalDuration + n.getSetEndTime()-n.getSetStartTime();
                        if(n.getEndStartTime() != -1)
                        {
                            totalBreakTaken += n.getBreakAmt();

                            int studyEndTime = n.getSetEndTime();
                            if(n.getEndEndTime() != -1)
                            {
                                studyEndTime = n.getEndEndTime();
                            }

                            usedDuration += studyEndTime - n.getEndStartTime();


                        }



                        Log.d(TAG, "onChanged: "+n.getSubject());
                    }

                }
                else{
                    SavedNotes notes = new SavedNotes(0, "day", "", 1, 1, 1, 1, 1, "", false);
                    savedNotesList.add(notes);

                    progressDayAdapter.setNotesList(savedNotesList);

                }

            }
        });

    }


}