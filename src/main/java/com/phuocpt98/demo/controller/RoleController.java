package com.phuocpt98.demo.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.phuocpt98.demo.ApiResponse.ApiResponse;
import com.phuocpt98.demo.dto.request.RoleRequest;
import com.phuocpt98.demo.dto.response.RoleResponse;
import com.phuocpt98.demo.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllPermissions(){
        return ApiResponse.success(roleService.getAll());
    }

    @PostMapping
    ApiResponse<RoleResponse> created(@RequestBody RoleRequest roleRequest){
        return ApiResponse.success(roleService.create(roleRequest));
    }

    @DeleteMapping(value = "/{roleId}")
    ApiResponse<?> delete(@PathVariable Long roleId){
        roleService.delete(roleId);
        return ApiResponse.success("Deleted successfully");
    }

}
