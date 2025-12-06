package com.phuocpt98.demo.controller;

import com.phuocpt98.demo.ApiResponse.ApiResponse;
import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.request.UserUpdateRequest;
import com.phuocpt98.demo.dto.response.UserResponse;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.repository.UserRepository;
import com.phuocpt98.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        log.info("controller Create user");
        return ApiResponse.success(userService.create(userCreationRequest));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<User>> getAllUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User: {}", authentication.getName());
        log.info("Roles: {}", authentication.getAuthorities());
        return ApiResponse.success(userService.getAllUsers());
    }

    @GetMapping("/users/{userId}")
//    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and userId = authentication.principal.id)")
    public ApiResponse<User> getUserById(@PathVariable Long userId) {
        return ApiResponse.success(userService.getUserById(userId));
    }

    @PutMapping("users/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ApiResponse.success(userService.updateUser(userId, userUpdateRequest));
    }

    @DeleteMapping("users/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable Long userId) {
         userService.deleteUser(userId);
         return ApiResponse.success("User deleted successfully");
    }

    @GetMapping("users/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo());
    }
}
