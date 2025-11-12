package com.example.onlineCourses.repository;

import com.example.onlineCourses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//import org.springframework.security.core.userdetails.User;  vi sao sai***

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);



}
