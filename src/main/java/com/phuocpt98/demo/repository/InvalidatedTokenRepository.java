package com.phuocpt98.demo.repository;

import com.phuocpt98.demo.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository  extends JpaRepository<InvalidatedToken, String> {

    boolean existsById(String id);
}
