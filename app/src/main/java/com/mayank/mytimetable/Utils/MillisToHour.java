package com.mayank.mytimetable.Utils;

public class MillisToHour {



    public static String convertToString(int timeInMillis)
    {


        String startHour =  String.valueOf(timeInMillis/60);
        if(startHour.length() == 1)
        {
            startHour = '0'+startHour;
        }
        return startHour;

    }
}
