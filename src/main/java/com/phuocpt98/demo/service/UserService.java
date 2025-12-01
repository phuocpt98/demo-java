package com.phuocpt98.demo.service;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.request.UserUpdateRequest;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.exception.AppException;
import com.phuocpt98.demo.exception.ErrorCode;
import com.phuocpt98.demo.mapper.UserMapper;
import com.phuocpt98.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private UserRepository userRepository;

    private UserMapper userMapper;


    public User create(UserCreationRequest request) {
        if(userRepository.existsByFullName(request.getFull_name())){
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        request.setPassword(encoder.encode(request.getPassword()));

        User user = userMapper.toUser(request);
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
