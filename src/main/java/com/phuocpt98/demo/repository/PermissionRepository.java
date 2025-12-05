package com.phuocpt98.demo.repository;

import com.phuocpt98.demo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
