package com.phuocpt98.demo.repository;

import com.phuocpt98.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByFullName(String fullName);
}
