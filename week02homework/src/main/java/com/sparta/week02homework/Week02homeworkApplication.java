package com.sparta.week02homework;

import com.sparta.week02homework.domain.Person;
import com.sparta.week02homework.domain.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Week02homeworkApplication {

    // Week02Application.java 의 main 함수 아래에 붙여주세요.
    public static void main(String[] args) {
        SpringApplication.run(Week02homeworkApplication.class, args);
    }

}

