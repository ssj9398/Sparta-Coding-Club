package com.sparta.week01homework.controller;

import com.sparta.week01homework.prac.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @GetMapping("/myinfo")
    public Person getPerson(){
        Person person = new Person();
        person.setAddress("서울");
        person.setAge(28);
        person.setJob("Job");
        person.setName("SonSeongJin");
        return person;
    }
}
