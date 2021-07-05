package com.sparta.week01.prac;

public class Course {
    private String title;
    private int days;
    private String tutor;

    public Course() {
        this.title = title;
        this.tutor = tutor;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTutor() {
        return this.tutor;
    }

    public int getDays() {
        return this.days;
    }

}
