package com.phuocpt98.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.phuocpt98.demo.dto.request.RoleRequest;
import com.phuocpt98.demo.dto.response.RoleResponse;
import com.phuocpt98.demo.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleRequest roleRequest);


    RoleResponse toResponse(Role role);
}
