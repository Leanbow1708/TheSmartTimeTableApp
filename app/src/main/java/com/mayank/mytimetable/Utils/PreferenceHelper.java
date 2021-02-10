package com.mayank.mytimetable.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mayank.mytimetable.DataClass.DayDate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferenceHelper {


    SharedPreferences mSharedPreferences;
    Context mContext = null;
    public PreferenceHelper(Context context)
    {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("week_days", Context.MODE_PRIVATE);
    }

    public void setWeekDays(Integer i)
    {

        try {

            List<Integer> weekList = getWeekDays();
            if(!weekList.contains(i))
            {
                weekList.add(i);
                Gson gson = new Gson();
                String json = gson.toJson(weekList);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("set", json);
                editor.commit();


            }
        }

        catch (Exception e){

        }
    }


    public void insertDayDate(List<DayDate> list)
    {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString("daydate",json);
            editor.commit();
        }
        catch (Exception e)
        {

        }

    }

    public boolean shouldRefreshTheProgress(String day,String date)
    {

        try {
            List<DayDate> list = getDayDateList();

            if(list == null)
            {
                DayDate d = new DayDate(day, date);
                list.add(d);
                insertDayDate(list);
                return false;
            }

            else{

                for(int i = 0;i < list.size();i++)
                {

                    DayDate d = list.get(i);
                    if(d.getDay().equals(day))
                    {
                        if(!d.getDate().equals(date))
                        {

                            list.get(i).setDate(date);
                            insertDayDate(list);
                            /// now insert this date
                            return true;
                        }


                    }

                }


            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }


    }
    public List<DayDate> getDayDateList()
    {

        Gson gson = new Gson();
        List<DayDate> dayDates = new ArrayList<>();
        try {
            String json = mSharedPreferences.getString("daydate", "");
            if(!json.isEmpty())
            {
                Type type = new TypeToken<List<DayDate>>(){}.getType();
                dayDates = gson.fromJson(json, type);
            }
            return dayDates;
        }
        catch (Exception e)
        {
            return dayDates;
        }
    }


    public List<Integer> getWeekDays()
    {
        Gson gson = new Gson();
        List<Integer> weekList = new ArrayList<>();
        String json = mSharedPreferences.getString("set", "");

        if(!json.isEmpty())
        {
            Type type = new TypeToken<List<Integer>>(){}.getType();
            weekList = gson.fromJson(json, type);


        }

        return weekList;
    }

    public void removeThisDay(Integer i)
    {

        List<Integer> weekList = getWeekDays();
        weekList.remove(i);
        Gson gson = new Gson();
        String json = gson.toJson(weekList);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("set", json);
        editor.commit();


    }





}
