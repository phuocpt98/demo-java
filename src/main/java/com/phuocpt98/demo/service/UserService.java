package com.phuocpt98.demo.service;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.request.UserUpdateRequest;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.exception.AppException;
import com.phuocpt98.demo.exception.ErrorCode;
import com.phuocpt98.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(UserCreationRequest request) {
        User user = new User();

        if(userRepository.existsByFullName(request.getFull_name())){
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFull_name());
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow( () -> new AppException(ErrorCode.BAD_REQUEST));
    }

    public User updateUser(Long userId, UserUpdateRequest userUpdateRequest){
        User user = getUserById(userId);
        user.setPassword(userUpdateRequest.getPassword());
        user.setEmail(userUpdateRequest.getEmail());
        user.setFullName(userUpdateRequest.getFull_name());

        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
