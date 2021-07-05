package com.sparta.week01homework.prac;

public class Person {
    private String name;
    private int age;
    private String address;
    private String job;

    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public String getAddress(){
        return this.address;
    }
    public String getJob(){
        return this.job;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setAge(int age){
        this.age=age;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setJob(String job){
        this.job=job;
    }
}

