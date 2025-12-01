package com.phuocpt98.demo.repository;

import com.phuocpt98.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByFullName(String fullName);

    Optional<User> findByUsername(String username);
}
