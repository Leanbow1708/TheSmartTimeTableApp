package com.mayank.mytimetable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mayank.mytimetable.Fragments.AboutUsFragment;
import com.mayank.mytimetable.Fragments.AllDayProgressFragment;
import com.mayank.mytimetable.Fragments.AllDayTimeTableFragment;
import com.mayank.mytimetable.Fragments.CurrDayProgressFragment;
import com.mayank.mytimetable.Fragments.PlaceHolderFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   public static ShimmerFrameLayout shimmerAboutUs;
    private ShimmerFrameLayout mShimmerViewContainer;

    public static ConstraintLayout layoutSaveNote;
    public static MotionLayout layoutMotionActivity;
    public static LinearLayout layoutBottomNavigationView;
    LinearLayout layout_home;
    LinearLayout layout_progress;
    LinearLayout layout_time_table;
    LinearLayout layout_about_me;
    public static FrameLayout frameLayout;

    Button btnSave;
    Button btnCancel;

  public static ShimmerFrameLayout shimmerFrameLayout;

    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        shimmerAboutUs = findViewById(R.id.shimmer_about_us);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);

        frameLayout = findViewById(R.id.main_frame);
        layoutBottomNavigationView = findViewById(R.id.layout_primary);

        initViews();
        if(shimmerFrameLayout != null)
        shimmerFrameLayout.startShimmerAnimation();


        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new CurrDayProgressFragment()).commit();

    }

    LinearLayout.LayoutParams param;
    LinearLayout.LayoutParams param1;

    @Override
    protected void onStart() {
        super.onStart();

         param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.5f
        );
        final float scale = getResources().getDisplayMetrics().density;
        int dp10 = (int) (10 * scale + 0.5f);
        int dp5 = (int) (5 * scale + 0.5f);

        param.topMargin = dp10;
        param.bottomMargin = dp10;
        param.leftMargin = dp5;
        param.rightMargin = dp5;


         param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
        );
        param1.topMargin = dp10;
        param1.bottomMargin = dp10;
        param1.leftMargin = dp5;
        param1.rightMargin = dp5;

        layout_time_table.setLayoutParams(param1);
        layout_about_me.setLayoutParams(param1);
        layout_progress.setLayoutParams(param1);
        layout_home.setLayoutParams(param1);


    }

    private void initViews() {
        layout_about_me = findViewById(R.id.layout_about_me);
        layout_time_table = findViewById(R.id.layout_time_table);
        layout_progress = findViewById(R.id.layout_progress);
        layout_home = findViewById(R.id.layout_home);


        txtHome = findViewById(R.id.txt_home);
        txtProgress = findViewById(R.id.txt_progress);
        txtTimeTable = findViewById(R.id.txt_time_table);
        txtAboutMe = findViewById(R.id.txt_about_me);
        imgHome = findViewById(R.id.img_home);
        imgTimeTable = findViewById(R.id.img_time_table);
        imgProgress = findViewById(R.id.img_progress);
        imgAboutMe = findViewById(R.id.img_about_me);



        layout_home.setOnClickListener(this);
        layout_progress.setOnClickListener(this);
        layout_time_table.setOnClickListener(this);
        layout_about_me.setOnClickListener(this);


    }

    TextView txtHome;
    TextView txtProgress;
    TextView txtTimeTable;
    TextView txtAboutMe;
    ImageView imgHome;
    ImageView imgProgress;
    ImageView imgTimeTable;
    ImageView imgAboutMe;
    @Override
    public void onClick(View view) {


       if(MainActivity.shimmerAboutUs != null && MainActivity.shimmerFrameLayout!= null)
       {
           if(view.getId() == R.id.layout_about_me)
           {
               MainActivity.shimmerAboutUs.setVisibility(View.VISIBLE);
               MainActivity.shimmerAboutUs.startShimmerAnimation();
           }
           else{
               MainActivity.shimmerFrameLayout.setVisibility(View.VISIBLE);

               shimmerFrameLayout.startShimmerAnimation();
           }
       }



        layout_about_me.setBackgroundResource(R.drawable.white_bkg);
        layout_time_table.setBackgroundResource(R.drawable.white_bkg);
        layout_progress.setBackgroundResource(R.drawable.white_bkg);
        layout_home.setBackgroundResource(R.drawable.white_bkg);
        txtHome.setVisibility(View.GONE);

        txtProgress.setVisibility(View.GONE);

        txtTimeTable.setVisibility(View.GONE);

        txtAboutMe.setVisibility(View.GONE);
        imgHome.setImageResource(R.drawable.ic_home);
        imgProgress.setImageResource(R.drawable.ic_progress);
        imgTimeTable.setImageResource(R.drawable.ic_time_table);
        imgAboutMe.setImageResource(R.drawable.ic_person);



        switch (view.getId())
        {


            case R.id.layout_home:
                if(getSupportFragmentManager().getBackStackEntryCount() == 1)
                {
                    getSupportFragmentManager().popBackStack();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new CurrDayProgressFragment()).commit();


                layout_home.setBackgroundResource(R.drawable.bottom_navigation_selected_item_bkg);
                imgHome.setImageResource(R.drawable.ic_home_primary);
               txtHome.setTextColor(Color.parseColor("#9a84db"));
               txtHome.setVisibility(View.VISIBLE);


                break;
            case R.id.layout_progress:
                if(getSupportFragmentManager().getBackStackEntryCount() == 1)
                {
                    getSupportFragmentManager().popBackStack();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AllDayProgressFragment()).addToBackStack("1").commit();

                layout_progress.setBackgroundResource(R.drawable.bottom_navigation_selected_item_bkg);
                imgProgress.setImageResource(R.drawable.ic_progress_primary);
                txtProgress.setTextColor(Color.parseColor("#9a84db"));
                txtProgress.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_time_table:
                if(getSupportFragmentManager().getBackStackEntryCount() == 1)
                {
                    getSupportFragmentManager().popBackStack();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AllDayTimeTableFragment()).addToBackStack("3").commit();

                layout_time_table.setLayoutParams(param);
                layout_time_table.setBackgroundResource(R.drawable.bottom_navigation_selected_item_bkg);
                imgTimeTable.setImageResource(R.drawable.ic_time_table_primary);
                txtTimeTable.setTextColor(Color.parseColor("#9a84db"));
                txtTimeTable.setVisibility(View.VISIBLE);

                break;
            case R.id.layout_about_me:
                if(getSupportFragmentManager().getBackStackEntryCount() == 1)
                {
                    getSupportFragmentManager().popBackStack();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AboutUsFragment()).addToBackStack("4").commit();

                layout_about_me.setBackgroundResource(R.drawable.bottom_navigation_selected_item_bkg);
                imgAboutMe.setImageResource(R.drawable.ic_person_primary);
                txtAboutMe.setTextColor(Color.parseColor("#9a84db"));
                txtAboutMe.setVisibility(View.VISIBLE);

                break;
         }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(AllDayTimeTableFragment.isAddNotePopupOpen || CurrDayProgressFragment.isConfirmPopupOpen)
        {
            AllDayTimeTableFragment.closeAddNotePop();
            CurrDayProgressFragment.closeConfirmPopup();
        }

    }

    void refreshBottom()
    {

        layout_about_me.setBackgroundResource(R.drawable.white_bkg);
        layout_time_table.setBackgroundResource(R.drawable.white_bkg);
        layout_progress.setBackgroundResource(R.drawable.white_bkg);
        layout_home.setBackgroundResource(R.drawable.white_bkg);
        txtHome.setVisibility(View.GONE);

        txtProgress.setVisibility(View.GONE);

        txtTimeTable.setVisibility(View.GONE);

        txtAboutMe.setVisibility(View.GONE);
        imgHome.setImageResource(R.drawable.ic_home);
        imgProgress.setImageResource(R.drawable.ic_progress);
        imgTimeTable.setImageResource(R.drawable.ic_time_table);
        imgAboutMe.setImageResource(R.drawable.ic_person);
    }

    @Override
    public void onBackPressed() {

        if(AllDayTimeTableFragment.isAddNotePopupOpen || CurrDayProgressFragment.isConfirmPopupOpen)
        {
            AllDayTimeTableFragment.closeAddNotePop();
            CurrDayProgressFragment.closeConfirmPopup();
        }
        else{


            super.onBackPressed();
            refreshBottom();

            layout_home.setBackgroundResource(R.drawable.bottom_navigation_selected_item_bkg);
            imgHome.setImageResource(R.drawable.ic_home_primary);
            txtHome.setTextColor(Color.parseColor("#9a84db"));
            txtHome.setVisibility(View.VISIBLE);

//            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new CurrDayProgressFragment()).addToBackStack("main").commit();




    }

    }


}