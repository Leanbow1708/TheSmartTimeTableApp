package com.mayank.mytimetable.DataClass;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_note_table")
public class SavedNotes {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private boolean isBreakClicked = false;

    private int breakAmt;
    private String day;
    private String subject;
    private int category;
    private int setStartTime;
    private int setEndTime;
    private int endStartTime;
    private int endEndTime;
    private String description;
    private boolean isClicked = false;

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setSetStartTime(int setStartTime) {
        this.setStartTime = setStartTime;
    }

    public void setSetEndTime(int setEndTime) {
        this.setEndTime = setEndTime;
    }

    public void setEndStartTime(int endStartTime) {
        this.endStartTime = endStartTime;
    }

    public void setEndEndTime(int endEndTime) {
        this.endEndTime = endEndTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getSubject() {
        return subject;
    }

    public int getCategory() {
        return category;
    }

    public int getSetStartTime() {
        return setStartTime;
    }

    public int getSetEndTime() {
        return setEndTime;
    }

    public int getEndStartTime() {
        return endStartTime;
    }

    public int getEndEndTime() {
        return endEndTime;
    }

    public String getDescription() {
        return description;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public int getBreakAmt(){return breakAmt;}
    public void setBreakAmt(int breakAmt){this.breakAmt = breakAmt;}


    public SavedNotes( int breakAmt, String day, String subject, int category, int setStartTime, int setEndTime, int endStartTime, int endEndTime, String description, boolean isClicked) {
        this.isBreakClicked = false;
        this.breakAmt = breakAmt;
        this.day = day;
        this.subject = subject;
        this.category = category;
        this.setStartTime = setStartTime;
        this.setEndTime = setEndTime;
        this.endStartTime = endStartTime;
        this.endEndTime = endEndTime;
        this.description = description;
        this.isClicked = isClicked;
    }

    public boolean isBreakClicked() {
        return isBreakClicked;
    }

    public void setBreakClicked(boolean breakClicked) {
        isBreakClicked = breakClicked;
    }
}
