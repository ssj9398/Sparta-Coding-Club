package com.sparta.week01homework.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public class PersonRequestDto {
    private String name;
    private String job;
    private int age;
    private String address;
}
