package com.sparta.week02homework.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int age;

    public Person(PersonRequestDto requestDto){
        this.address= requestDto.getAddress();
        this.age = requestDto.getAge();
        this.job=requestDto.getJob();
        this.name=requestDto.getName();
        this.id=requestDto.getId();
    }
    public void update(PersonRequestDto requestDto){
        this.address= requestDto.getAddress();
        this.age = requestDto.getAge();
        this.job=requestDto.getJob();
        this.name=requestDto.getName();
    }

}
