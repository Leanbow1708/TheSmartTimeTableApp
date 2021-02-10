package com.mayank.mytimetable.Utils;

public class MillisToMin {

    public static String convertToString(int timeInMillis)
    {
       String startMin =  String.valueOf(timeInMillis%60);
        if(startMin.length() == 1)
            startMin = '0'+startMin;
        return startMin;
    }

}
