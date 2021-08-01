package com.sparta.springcore.repository;

import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserRole;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    public void create() {
        // 회원 "user1" 객체 추가
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@sprata.com");
        user1.setRole(UserRole.USER);

        // 회원 "user1" 객체의 ID 값이 없다가..
        userRepository.save(user1);
        // DB 에 저장된 후, USER ID 값이 생김

        // 테스트 회원 데이터 삭제
        userRepository.delete(user1);
    }

    @Order(2)
    @Test
    public void findUser() {
        // 회원 "user1" 객체 생성
        User instance1 = new User();
        // 회원 "user1" 객체 또 생성
        User instance2 = new User();
        // 회원 "user1" 객체 또또 생성
        User instance3 = new User();

        // User 클래스의 객체 3개는 다 다른 객체임
        System.out.println("instance1:" + instance1 + " ,instance2: " + instance2 + ", instance3:" + instance3);

        // ------------------------------------
        // 회원 "user1" 객체 추가
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@sprata.com");
        user1.setRole(UserRole.USER);
        // 회원 "user1" 객체를 영속화
        userRepository.save(user1);

        // 회원 "user1" 을 조회
        User foundUser1 = userRepository.findByUsername("user1").orElse(null);
        // 회원 "user1" 을 또 조회
        User foundUser2 = userRepository.findByUsername("user1").orElse(null);
        // 회원 "user1" 을 또또 조회
        User foundUser3 = userRepository.findByUsername("user1").orElse(null);

        System.out.println("foundUser1:" + foundUser1 + " ,foundUser2: " + foundUser2 + ", foundUser3:" + foundUser3);

        // ------------------------------------
        // 테스트 회원 데이터 삭제
        userRepository.delete(user1);
    }
}