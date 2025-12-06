package com.phuocpt98.demo.mapper;

import org.mapstruct.Mapper;

import com.phuocpt98.demo.dto.request.PermissionRequest;
import com.phuocpt98.demo.dto.response.PermissionResponse;
import com.phuocpt98.demo.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(PermissionRequest permissionRequest);


    PermissionResponse toResponse(Permission permission);
}
