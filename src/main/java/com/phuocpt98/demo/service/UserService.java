package com.phuocpt98.demo.service;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(UserCreationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFull_name());
        return userRepository.save(user);
    }
}
