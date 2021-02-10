package com.mayank.mytimetable.Fragments;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mayank.mytimetable.Adapters.HorizontalDaysAdapter;
import com.mayank.mytimetable.Adapters.MainAdapter;
import com.mayank.mytimetable.DataClass.Days;
import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.DataClass.Notes;
import com.mayank.mytimetable.MainActivity;
import com.mayank.mytimetable.R;
import com.mayank.mytimetable.Repository.NoteRepository;
import com.mayank.mytimetable.Utils.Constants;
import com.mayank.mytimetable.Utils.MillisToHour;
import com.mayank.mytimetable.Utils.MillisToMin;
import com.mayank.mytimetable.Utils.OnAlreadySetDayTTClickListener;
import com.mayank.mytimetable.Utils.OnDayClickListener;
import com.mayank.mytimetable.Utils.OnDeleteClickListener;
import com.mayank.mytimetable.Utils.OnEditClickListener;
import com.mayank.mytimetable.Utils.PreferenceHelper;
import com.mayank.mytimetable.ViewModel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AllDayTimeTableFragment extends Fragment {


    ImageView imgDeleteAll;

    int update_endTime = 0;
    int update_startTime = 0;

    EditText edt_notes;
    ImageView imgCancel;
    String date;
    public static MotionLayout motionLayout;
    String mon;
    String year;
    List<Note> updatedList = new ArrayList<>();
    int idToBeUpdated = 0;
    ImageView imgCategory;
    List<Integer> daysThatAreInserted = new ArrayList<>();

    private String[] dayOfWeek = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public static boolean retrievedPreDayNotes = true;

    private static final String TAG = "MainActivity";
    TextView txt_curr_day;
    TextView txt_curr_date;
    MainAdapter adapter;
    RecyclerView recycler_main;
    Button btnAdd;
    ArrayList<Notes> list = new ArrayList<>();
    FloatingActionButton fab;
    OnDayClickListener mOnDayClickListener;
    RecyclerView recyclerDay;
    HorizontalDaysAdapter daysAdapter;
    ArrayList<Days> daysList;
    TextView txtFrom;
    TextView txtTo;
    int fromTime = -1;
    int t_time = 0;
    TextView txtTotalDuration;


    int toTime = -1;
    boolean t1Set = false;
    boolean t2Set = false;
    Spinner spinnerCategory;

    NoteViewModel noteViewModel;
    EditText edtNotes;
    EditText edtSubject;
    public static boolean isAddNotePopupOpen = false;
    int categoryItem = 0;

    PreferenceHelper helper;

    public AllDayTimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public static void closeAddNotePop() {
        isAddNotePopupOpen = false;
        if (motionLayout != null)
            motionLayout.transitionToStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_all_day_time_table, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NoteRepository.getInstance(getActivity().getApplication());


        daysList = new ArrayList<>();
        helper = new PreferenceHelper(getContext());
        imgCategory = getActivity().findViewById(R.id.imgCategory);
        txt_curr_day = getActivity().findViewById(R.id.txt_curr_day);
        txt_curr_date = getActivity().findViewById(R.id.txt_curr_date);

        imgDeleteAll = getActivity().findViewById(R.id.img_delete_all);

        edtNotes = getActivity().findViewById(R.id.edt_notes);
        edtSubject = getActivity().findViewById(R.id.edt_subject);
        fab = getActivity().findViewById(R.id.fab);
        txtFrom = getActivity().findViewById(R.id.txt_from);
        txtTo = getActivity().findViewById(R.id.txt_to);
        spinnerCategory = getActivity().findViewById(R.id.spnr_category);

        motionLayout = getActivity().findViewById(R.id.motion_layout_1);
        recyclerDay = getActivity().findViewById(R.id.recycler_day);
        daysAdapter = new HorizontalDaysAdapter();
        btnAdd = getActivity().findViewById(R.id.btn_add);

        txtTotalDuration = getActivity().findViewById(R.id.txt_total_duration);
        imgCancel = getActivity().findViewById(R.id.img_cancel);
        recycler_main = getActivity().findViewById(R.id.recyler_main);
        edt_notes = getActivity().findViewById(R.id.edt_notes);
        adapter = new MainAdapter();


    }


    @Override
    public void onStart() {
        super.onStart();


        if(MainActivity.shimmerFrameLayout != null)
        {
            MainActivity.shimmerFrameLayout.stopShimmerAnimation();
            MainActivity.shimmerFrameLayout.setVisibility(View.GONE);

        }

        imgDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar snackbar = Snackbar
                        .make(getView(), "All slots will be deleted", Snackbar.LENGTH_SHORT)

                        .setAction("Confirm!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                helper.removeThisDay(Constants.day - 1);


                                noteViewModel.deleteAll(Constants.dayOfWeek[Constants.day - 1]);
//                                Snackbar mSnackbar = Snackbar.make(getView(), "Message successfully deleted.", Snackbar.LENGTH_SHORT);
//                                mSnackbar.show();
                            }
                        });

                snackbar.show();




            }
        });
        fillDayList();
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Select a category")) {
                    categoryItem = 0;
//                    Toast.makeText(MainActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
                } else {
                    categoryItem = i;
                    imgCategory.setImageResource(Constants.categoryDrawables[i - 1]);

                    TextView txt = (TextView) view;
                    txt.setTextColor(Color.parseColor("#000000"));
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fillTheCategory();

        getDayNumber();

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                t1Set = true;

                                int x = hourOfDay * 60 + minute;
                                fromTime = x;
                                if (t1Set && t2Set) {

                                    if (fromTime > toTime) {
                                        Toast.makeText(getActivity(), "start time should be lesser than end", Toast.LENGTH_SHORT).show();
                                    }


                                    if (fromTime >= toTime) {
                                        t_time = 24 * 60 - fromTime + toTime;
                                    } else {
                                        t_time = toTime - fromTime;
                                    }
                                    String hr1 = MillisToHour.convertToString(t_time);
                                    String min1 = MillisToMin.convertToString(t_time);

                                    txtTotalDuration.setText("Total duration - \n" + hr1 + " hr " + min1 + " min");

//
//                                    if(fromTime > toTime)
//                                    {
//                                        Toast.makeText(getActivity(), "Invalid time period", Toast.LENGTH_SHORT).show();
//                                    }


                                }

                                String hr = MillisToHour.convertToString(x);
                                String min = MillisToMin.convertToString(x);

                                txtFrom.setText(hr + ":" + min);


                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();


            }
        });

        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                t2Set = true;
                                int x = hourOfDay * 60 + minute;
                                toTime = x;

                                if (t1Set && t2Set) {

                                    if (fromTime >= toTime) {
                                        Toast.makeText(getActivity(), "start time should be lesser than end", Toast.LENGTH_SHORT).show();
                                    }


                                    if (fromTime > toTime) {
                                        t_time = 24 * 60 - fromTime + toTime;
                                    } else {
                                        t_time = toTime - fromTime;
                                    }
                                    String hr1 = MillisToHour.convertToString(t_time);
                                    String min1 = MillisToMin.convertToString(t_time);

                                    txtTotalDuration.setText("Total duration - \n" + hr1 + " hr " + min1 + " min");


//                                    if(fromTime > toTime)
//                                    {
//                                        Toast.makeText(getActivity(), "Invalid time period", Toast.LENGTH_SHORT).show();
//                                    }
//                                    else{
//
//                                    }

                                }


                                String hr = MillisToHour.convertToString(x);
                                String min = MillisToMin.convertToString(x);

                                txtTo.setText(hr + ":" + min);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();


            }
        });


        recyclerDay.setAdapter(daysAdapter);
        recyclerDay.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerDay.getLayoutManager().scrollToPosition(Constants.day-1);

        daysAdapter.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(int t) {

                if(t >= 0 && t < 7)
                {
                    Constants.day = t + 1;
                    noteViewModel.selectQuery();

                    for (int i = 0; i < daysList.size(); i++) {
                        daysList.get(i).setCLicked(false);
                    }
                    daysList.get(t).setCLicked(true);
                    daysAdapter.notifyDataSetChanged();
                    // Constants.day = t;
                    Log.d(TAG, "onDayClick: " + daysList.get(t).getDay() + "  " + t);
                }


            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAddNotePopupOpen = false;
                String notes = edtNotes.getText().toString();
                String subject = edtSubject.getText().toString();

                if ((notes != null && notes.length() > 0) && (subject != null && subject.length() > 0) && categoryItem != 0 && fromTime < toTime) {

                    helper.setWeekDays(Constants.day - 1);


                    Note n = new Note(0, dayOfWeek[Constants.day - 1], subject, categoryItem, fromTime, toTime, -1, -1, notes, false);


                    if (btnAdd.getText().equals("ADD")) {
                        noteViewModel.insert(n);
                    } else {
                        n.setId(idToBeUpdated);
                        noteViewModel.update(n);
                    }

                    update_endTime = -1;
                    update_endTime = -1;
                    noteViewModel.selectQuery();

                    t1Set = false;
                    t2Set = false;

                    edt_notes.setText("");
                    edtSubject.setText("");
                    txtFrom.setText("From");
                    txtTo.setText("To");


                    Toast.makeText(getActivity(), "Notes inserted", Toast.LENGTH_SHORT).show();
                    spinnerCategory.setSelection(0);
                    motionLayout.transitionToStart();


                }
                else{

                    if(fromTime >= toTime)
                    {
                        Toast.makeText(getContext(), "start time must be less than end", Toast.LENGTH_SHORT).show();
                    }
                    else if(notes == null || notes.length() == 0)
                    {
                        Toast.makeText(getContext(), "Note field can't be empty", Toast.LENGTH_SHORT).show();

                    }
                    else if(subject == null || subject.length() == 0)
                    {
                        Toast.makeText(getContext(), "Subject field can't be empty", Toast.LENGTH_SHORT).show();

                    }
                    else if(categoryItem == 0)
                    {
                        Toast.makeText(getContext(), "Please choose a valid category", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                motionLayout.transitionToStart();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setText("ADD");
                isAddNotePopupOpen = true;
                motionLayout.transitionToEnd();

                update_endTime = -1;
                update_startTime = -1;
            }
        });

        edt_notes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.edt_notes) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });


//        recycler_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if (!recyclerView.canScrollVertically(1)) {
//                    Toast.makeText(getContext(), "Last", Toast.LENGTH_SHORT).show();
//                    fab.setVisibility(View.GONE);
//                }
//                else
//                    fab.setVisibility(View.VISIBLE);
//            }
//        });


//        adapter.setList(list);
        recycler_main.setAdapter(adapter);
        recycler_main.setLayoutManager(new LinearLayoutManager(getActivity()));


        adapter.setOnDeleteClickListener(new OnDeleteClickListener() {
            @Override
            public void onDeleteClickListener(int i, int size) {

                if (size == 0) {
                    helper.removeThisDay(Constants.day - 1);
                }
                noteViewModel.delete(updatedList.get(i));
                Log.d(TAG, "onDeleteClickListener: " + updatedList.get(i).getSubject());

            }
        });

        adapter.setOnEditClickListener(new OnEditClickListener() {
            @Override
            public void onEditClick(Note n) {

                isAddNotePopupOpen = true;

                imgCategory.setImageResource(Constants.categoryDrawables[n.getCategory() - 1]);

                btnAdd.setText("UPDATE");
                edtNotes.setText(n.getDescription());
                edtSubject.setText(n.getSubject());

                spinnerCategory.setSelection(n.getCategory());

                fromTime = n.getSetStartTime();
                toTime = n.getSetEndTime();

                update_startTime = n.getEndStartTime();
                update_endTime = n.getEndEndTime();

                String startHr = MillisToHour.convertToString(n.getSetStartTime());
                String startMin = MillisToMin.convertToString(n.getSetStartTime());
                String endHr = MillisToHour.convertToString(n.getSetEndTime());
                String endMin = MillisToMin.convertToString(n.getSetEndTime());


                txtFrom.setText(startHr + ":" + startMin);
                txtTo.setText(endHr + ":" + endMin);


                idToBeUpdated = n.getId();

                motionLayout.transitionToEnd();


            }
        });

        adapter.setOnAlreadySetDayTTClickListener(new OnAlreadySetDayTTClickListener() {
            @Override
            public void OnClickListener(int i) {

                int day = daysThatAreInserted.get(i - 1);
                Log.d(TAG, "OnClickListener: " + day);

                retrievedPreDayNotes = false;
                noteViewModel.selectAllNotesOfADay(day);


            }
        });


    }


    private void fillDayList() {
        Days d1 = new Days("Sunday", false);
        Days d2 = new Days("Monday", false);
        Days d3 = new Days("Tuesday", false);
        Days d4 = new Days("Wednesday", false);
        Days d5 = new Days("Thursday", false);
        Days d6 = new Days("Friday", false);
        Days d7 = new Days("Saturday", false);

        daysList = new ArrayList<>();
        daysList.add(d1);
        daysList.add(d2);
        daysList.add(d3);
        daysList.add(d4);
        daysList.add(d5);
        daysList.add(d6);
        daysList.add(d7);

        daysAdapter.setDaysList(daysList);

    }

    private void getDayNumber() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


        String displayDate = dateFormat.format(date);

        txt_curr_date.setText(displayDate);


        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Constants.day = cal.get(Calendar.DAY_OF_WEEK);
        daysList.get(Constants.day - 1).setCLicked(true);

        Log.d(TAG, "getDayNumber: " + Constants.day);
        txt_curr_day.setText(dayOfWeek[Constants.day - 1]);


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        attachObservers();
        noteViewModel.selectQuery();


    }

    private void attachObservers() {


        noteViewModel.getAllNotesOfDay().observe(getActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {


                List<Note> list = new ArrayList<>();

                if (notes != null && notes.size() > 0 && retrievedPreDayNotes == false) {

                    for (Note n : notes) {
                        Note note = new Note(n.getBreakAmt(), Constants.dayOfWeek[Constants.day - 1], n.getSubject(), n.getCategory(), n.getSetStartTime(), n.getSetEndTime(), -1, -1, n.getDescription(), false);
                        list.add(note);
                        Log.d(TAG, "previous day " + n.getDay());
                    }

                    noteViewModel.insertEntireDayNote(list);
                    //    noteViewModel.selectQuery();

                    // so that multiple clicks can't happen
                    retrievedPreDayNotes = true;

                    helper.setWeekDays(Constants.day - 1);


                }


            }
        });

        noteViewModel.getAllNotes().observe(getActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                Log.d(TAG, "onChanged: " + "list changed");

                if (notes != null && notes.size() > 0) {
                    retrievedPreDayNotes = true;

                    for (Note n : notes) {
                        Log.d(TAG, "onChanged: " + n.getDay());
                    }


                }
                if (notes == null || notes.size() == 0) {
                    Note n = new Note(0, "day", "", categoryItem, 1, 1, 1, 1, "", false);
                    List<Note> list = new ArrayList<>();
                    list.add(n);


                    List<Integer> setDaysList = new ArrayList<>();
                    setDaysList = helper.getWeekDays();
                    Collections.sort(setDaysList);
                    daysThatAreInserted = setDaysList;

                    for (Integer i : setDaysList) {
                        Log.d(TAG, "onChanged:efwet " + i);
                        Note n1 = new Note(0, "set", Constants.dayOfWeek[i], i, 1, 1, 1, 1, "", false);
                        list.add(n1);
                    }


                    adapter.setList(list);

                } else {
                    updatedList = notes;
                    adapter.setList(notes);


                }


            }
        });


    }

    private void fillTheCategory() {

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Select a category");
        categoryList.add("Education");
        categoryList.add("Excercise");
        categoryList.add("Others");

        ArrayAdapter categoryAdapter = new ArrayAdapter(getActivity(), R.layout.row_spinner_category, categoryList);
        spinnerCategory.setAdapter(categoryAdapter);


    }
}
