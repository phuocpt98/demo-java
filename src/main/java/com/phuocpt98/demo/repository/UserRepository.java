package com.phuocpt98.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phuocpt98.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByFullName(String fullName);

    Optional<User> findByUsername(String username);
}
