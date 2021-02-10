package com.mayank.mytimetable.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mayank.mytimetable.Adapters.CurrDayTimeTableAdapter;
import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.DataClass.SavedNotes;
import com.mayank.mytimetable.MainActivity;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Repository.NoteRepository;
import com.mayank.mytimetable.Utils.Constants;
import com.mayank.mytimetable.Utils.OnAddBreakClickListener;
import com.mayank.mytimetable.Utils.OnStartEndClickListener;
import com.mayank.mytimetable.Utils.PreferenceHelper;
import com.mayank.mytimetable.ViewModel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CurrDayProgressFragment extends Fragment {


   public static MotionLayout motionLayout;
    private static final String TAG = "CurrDayProgressFragment";
    public static boolean isConfirmPopupOpen = false;

    private FloatingActionButton btnSaveProgress;

    private static List<Note> noteBreakList = new ArrayList<>();
    public CurrDayProgressFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RecyclerView recyclerView;
    CurrDayTimeTableAdapter currDayTimeTableAdapter;
   static NoteViewModel noteViewModel;

   PreferenceHelper helper;
   Button btnSave;
   Button btnClose;
  public static int todayDay = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curr_day_progress, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NoteRepository.getInstance(getActivity().getApplication());

        helper = new PreferenceHelper(getContext());
        motionLayout = getActivity().findViewById(R.id.motion_layout_fragment);
        btnSaveProgress = getActivity().findViewById(R.id.btn_save_todays_note);
        recyclerView = getActivity().findViewById(R.id.recycler_curr_day);
        currDayTimeTableAdapter = new CurrDayTimeTableAdapter();
        btnSave = getActivity().findViewById(R.id.btn_save);
        btnClose = getActivity().findViewById(R.id.btn_close);

    }


    @Override
    public void onPause() {
        super.onPause();
        isConfirmPopupOpen = false;

        if(motionLayout != null)
        {
            motionLayout.transitionToStart();
        }

    }

    public static void openConfirmSavePopup()
    {
        isConfirmPopupOpen = true;


        if(motionLayout!= null)
        {
            motionLayout.transitionToEnd();
        }





    }
    public static void closeConfirmPopup()
    {

        isConfirmPopupOpen = false;

        if(motionLayout != null)
        {
            motionLayout.transitionToStart();
        }



    }

    public static void saveTheProgress()
    {

        List<SavedNotes> list = new ArrayList<>();

        for(Note n : noteBreakList)
        {
            SavedNotes s = new SavedNotes(n.getBreakAmt(), n.getDay(), n.getSubject(), n.getCategory(), n.getSetStartTime(), n.getSetEndTime(), n.getEndStartTime(), n.getEndEndTime(), n.getDescription(), n.isClicked());
            list.add(s);
        }
        noteViewModel.insertSavedNote(list, Constants.dayOfWeek[todayDay-1]);


    }

    @Override
    public void onStart() {
        super.onStart();


        if(MainActivity.shimmerFrameLayout != null)
        {
            MainActivity.shimmerFrameLayout.stopShimmerAnimation();
            MainActivity.shimmerFrameLayout.setVisibility(View.GONE);

        }

        recyclerView.setNestedScrollingEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTheProgress();closeConfirmPopup();
                Toast.makeText(getContext(), "Progress saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeConfirmPopup();
            }
        });


        btnSaveProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    openConfirmSavePopup();

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(currDayTimeTableAdapter);
        getCurrentDayOfWeek();

        currDayTimeTableAdapter.setAddBreakClickListener(new OnAddBreakClickListener() {
            @Override
            public void onAddBreakClickListener(Integer i, Integer amnt, TextView txtBreak,int s,int e) {


                if(amnt > e-s)
                {
                    Toast.makeText(getActivity(), "you can't add break of this much duration",Toast.LENGTH_SHORT).show();
                }else{

                    if(amnt > 0)
                        txtBreak.setText("You have added "+amnt+" min(s) of break");
                    else
                        txtBreak.setText("Add a break");

                    Log.d(TAG, "onAddBreakClickListener: you have set "+amnt+" minutes"+"the position is "+i);

                    Note t = noteBreakList.get(i);
                    noteBreakList.get(i).setBreakAmt(amnt);


                    Note n = new Note(amnt,t.getDay(),t.getSubject(),t.getCategory(),t.getSetStartTime(),t.getSetEndTime(),t.getEndStartTime(),t.getEndEndTime(),t.getDescription(),t.isClicked());

                    n.setId(t.getId());
                    noteViewModel.update(n);
                    // noteViewModel.selectAllNotesOfADay(todayDay-1);

                    Log.d(TAG, "onAddBreakClickListener: "+noteBreakList.get(i).getSubject());

                }




            }
        });

        currDayTimeTableAdapter.setStartEndListener(new OnStartEndClickListener() {
            @Override
            public void onStartEndClickListener(char c, int time,String msg,Integer i) {

                if(msg!=null && msg.length()>0)
                {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
                else if(c == 's')
                {
                    Note n = noteBreakList.get(i);

                    n.setEndStartTime(time);

                    noteViewModel.update(n);
              }
                else if(c == 'e')
                {

                    Log.d(TAG, "onStartEndClickListener: "+"update for end time is called");
                    Note n = noteBreakList.get(i);
                            n.setEndEndTime(time);
                    noteViewModel.update(n);
                }



            }
        });


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

        Log.d(TAG, "getDayNumber: "+Constants.day);

        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        attachObservers();

        // here I am selecting the current day of time table to display on the home page



        if(helper.shouldRefreshTheProgress(Constants.dayOfWeek[todayDay-1],displayDate))
        {
            noteViewModel.updateEntireDay(Constants.dayOfWeek[todayDay-1]);
        }
        else{
            noteViewModel.selectAllNotesOfADay(todayDay-1);

        }




       // txt_curr_day.setText(dayOfWeek[Constants.day-1]);

    }

    private void attachObservers() {


        noteViewModel.getIsUpdateDone().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1)
                {
                    noteViewModel.selectAllNotesOfADay(todayDay-1);

                }
            }
        });

        noteViewModel.getAllNotesOfDay().observe(getActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> list) {

                if(list!=null && list.size()>0 && list.get(0).getDay().equals(Constants.dayOfWeek[todayDay-1]))
                {


                    noteBreakList = new ArrayList<>();

                    noteBreakList = list;

                    for(Note n : list)
                    {
                        Log.d(TAG, "onChanged: the endStartTime is "+n.getEndStartTime());
                    }


                    Log.d(TAG, "onChanged: "+list.size());
                    currDayTimeTableAdapter.setNoteList(list);
                }
                else{
                    Note note = new Note(0, "day", "", 1, -1, -1,-1,-1,"", false);
                    noteBreakList = new ArrayList<>();
                    noteBreakList.add(note);
            currDayTimeTableAdapter.setNoteList(noteBreakList);                }




            }
        });

    }

}