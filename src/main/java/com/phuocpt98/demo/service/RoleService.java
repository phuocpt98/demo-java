package com.phuocpt98.demo.service;


import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.phuocpt98.demo.dto.request.RoleRequest;
import com.phuocpt98.demo.dto.response.RoleResponse;
import com.phuocpt98.demo.entity.Role;
import com.phuocpt98.demo.mapper.RoleMapper;
import com.phuocpt98.demo.repository.PermissionRepository;
import com.phuocpt98.demo.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest roleRequest){
        Role role = roleMapper.toEntity(roleRequest);

        var permissions = permissionRepository.findAllById(roleRequest.getPermission_id());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);

        return roleMapper.toResponse(role);
    }

    public List<RoleResponse> getAll(){
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toResponse).toList();
    }

    public void delete(Long permissionId){
        roleRepository.deleteById(permissionId);
    }
}
