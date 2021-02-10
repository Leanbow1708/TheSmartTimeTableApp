package com.mayank.mytimetable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mayank.mytimetable.DataClass.WeekDay;

import java.util.List;

@Dao
public interface SetWeekDaysDao {

    @Insert
    void insert(WeekDay d);

    @Query("select * from week_day_table")
    List<WeekDay> getWeekDays();

}
