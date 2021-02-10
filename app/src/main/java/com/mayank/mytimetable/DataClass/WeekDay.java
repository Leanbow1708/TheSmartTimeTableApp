package com.mayank.mytimetable.DataClass;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "week_day_table")
public class WeekDay {

    @PrimaryKey
    private int week_day;

    public int getWeek_day() {
        return week_day;
    }

    public void setWeek_day(int week_day) {
        this.week_day = week_day;
    }

    public WeekDay(int week_day) {
        this.week_day = week_day;
    }
}
