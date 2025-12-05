package com.phuocpt98.demo.service;


import com.phuocpt98.demo.dto.request.PermissionRequest;
import com.phuocpt98.demo.dto.response.PermissionResponse;
import com.phuocpt98.demo.entity.Permission;
import com.phuocpt98.demo.mapper.PermissionMapper;
import com.phuocpt98.demo.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest permissionRequest){
        Permission permission = permissionMapper.toEntity(permissionRequest);
        permissionRepository.save(permission);
        return permissionMapper.toResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toResponse).toList();
    }

    public void delete(Long permissionId){
        permissionRepository.deleteById(permissionId);
    }
}
