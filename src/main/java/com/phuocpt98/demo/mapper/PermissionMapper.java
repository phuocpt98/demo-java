package com.phuocpt98.demo.mapper;

import com.phuocpt98.demo.dto.request.PermissionRequest;
import com.phuocpt98.demo.dto.response.PermissionResponse;
import com.phuocpt98.demo.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(PermissionRequest permissionRequest);


    PermissionResponse toResponse(Permission permission);
}
