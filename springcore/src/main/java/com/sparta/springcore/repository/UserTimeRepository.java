package com.sparta.springcore.repository;

import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTimeRepository extends JpaRepository<UserTime, Long> {
    UserTime findByUser(User user);
}