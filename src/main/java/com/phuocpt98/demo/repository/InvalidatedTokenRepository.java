package com.phuocpt98.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phuocpt98.demo.entity.InvalidatedToken;

public interface InvalidatedTokenRepository  extends JpaRepository<InvalidatedToken, String> {

    boolean existsById(String id);
}
