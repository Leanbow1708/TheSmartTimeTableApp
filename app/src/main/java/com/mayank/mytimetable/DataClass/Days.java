package com.mayank.mytimetable.DataClass;

public class Days {

    public String day;
    public boolean isCLicked;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isCLicked() {
        return isCLicked;
    }

    public void setCLicked(boolean CLicked) {
        isCLicked = CLicked;
    }

    public Days(String day, boolean isCLicked) {
        this.day = day;
        this.isCLicked = isCLicked;
    }
}
