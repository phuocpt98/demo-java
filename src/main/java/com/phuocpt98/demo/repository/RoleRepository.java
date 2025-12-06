package com.phuocpt98.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phuocpt98.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
