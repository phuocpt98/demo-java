package com.phuocpt98.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phuocpt98.demo.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
