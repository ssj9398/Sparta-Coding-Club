package com.sparta.week01homework.service;

import com.sparta.week01homework.domain.Person;
import com.sparta.week01homework.domain.PersonRepository;
import com.sparta.week01homework.domain.PersonRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public Long update(Long id, PersonRequestDto requestDto) {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 존재하지 않습니다.")
        );
        person.update(requestDto);
        return person.getId();
    }
}