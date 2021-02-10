package com.mayank.mytimetable.DataClass;

public class NoteBreak {


    private int id;
    private String day;
    private String subject;
    private int category;
    private int setStartTime;
    private int setEndTime;
    private int endStartTime;
    private int endEndTime;
    private String description;
    private boolean isClicked = false;
    private boolean isBreakClicked = false;


    public NoteBreak(String day, String subject, int category, int setStartTime, int setEndTime, int endStartTime, int endEndTime, String description, boolean isClicked, boolean isBreakClicked) {
        this.day = day;
        this.subject = subject;
        this.category = category;
        this.setStartTime = setStartTime;
        this.setEndTime = setEndTime;
        this.endStartTime = endStartTime;
        this.endEndTime = endEndTime;
        this.description = description;
        this.isClicked = isClicked;
        this.isBreakClicked = isBreakClicked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSetStartTime() {
        return setStartTime;
    }

    public void setSetStartTime(int setStartTime) {
        this.setStartTime = setStartTime;
    }

    public int getSetEndTime() {
        return setEndTime;
    }

    public void setSetEndTime(int setEndTime) {
        this.setEndTime = setEndTime;
    }

    public int getEndStartTime() {
        return endStartTime;
    }

    public void setEndStartTime(int endStartTime) {
        this.endStartTime = endStartTime;
    }

    public int getEndEndTime() {
        return endEndTime;
    }

    public void setEndEndTime(int endEndTime) {
        this.endEndTime = endEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isBreakClicked() {
        return isBreakClicked;
    }

    public void setBreakClicked(boolean breakClicked) {
        isBreakClicked = breakClicked;
    }
}
