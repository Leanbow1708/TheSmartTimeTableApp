package com.mayank.mytimetable.Utils;

import com.mayank.mytimetable.R;

import java.util.Calendar;

public class Constants {

    public static int hr24 = 0;
    public static int min = 0;
    public static String sDay;
    public static int day = 1;
    public static int[] categoryDrawables = new int[]{
            R.drawable.ic_book_open,
            R.drawable.ic_excercise,
            R.drawable.ic_other
    };

    public static String[] dayOfWeek = new String[]{"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    public static String[] dayOfWeek2 = new String[]{"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};

    public static void currentTime24Form()
    {
        Calendar c = Calendar.getInstance();

        int Hr24=c.get(Calendar.HOUR_OF_DAY);
        int Min=c.get(Calendar.MINUTE);

        hr24 = Hr24;
        min = Min;
    }






}
