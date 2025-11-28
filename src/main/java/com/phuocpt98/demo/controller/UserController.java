package com.phuocpt98.demo.controller;

import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.repository.UserRepository;
import com.phuocpt98.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return userService.create(userCreationRequest);
    }
}
