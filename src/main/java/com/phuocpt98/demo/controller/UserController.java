package com.phuocpt98.demo.controller;

import com.phuocpt98.demo.ApiResponse.ApiResponse;
import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.request.UserUpdateRequest;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.repository.UserRepository;
import com.phuocpt98.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        return ApiResponse.success(userService.create(userCreationRequest));
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userId, userUpdateRequest);
    }

    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable Long userId) {
         userService.deleteUser(userId);
         return "User deleted successfully";
    }
}
