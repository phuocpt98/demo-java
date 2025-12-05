package com.phuocpt98.demo.controller;


import com.phuocpt98.demo.ApiResponse.ApiResponse;
import com.phuocpt98.demo.dto.request.PermissionRequest;
import com.phuocpt98.demo.dto.response.PermissionResponse;
import com.phuocpt98.demo.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions(){
        return ApiResponse.success(permissionService.getAll());
    }

    @PostMapping
    ApiResponse<PermissionResponse> created(@RequestBody PermissionRequest permissionRequest){
        return ApiResponse.success(permissionService.create(permissionRequest));
    }

    @DeleteMapping(value = "/{permissionId}")
    ApiResponse<?> delete(@PathVariable Long permissionId){
        permissionService.delete(permissionId);
        return ApiResponse.success("Deleted successfully");
    }



}
