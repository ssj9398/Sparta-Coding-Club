package com.sparta.week01.prac;

public class Tutor {

    public Tutor(){

    }

    public Tutor(String name, String bio){
        this.name = name;
        this.bio = bio;
    }
    private String name;
    private String bio;

    public String getName(){
        return this.name;
    }
    public String getBio(){
        return this.bio;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setBio(String bio){
        this.bio = bio;
    }

}
