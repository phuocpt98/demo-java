package com.phuocpt98.demo.service;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.request.UserUpdateRequest;
import com.phuocpt98.demo.dto.response.UserResponse;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.enums.Role;
import com.phuocpt98.demo.exception.AppException;
import com.phuocpt98.demo.exception.ErrorCode;
import com.phuocpt98.demo.mapper.UserMapper;
import com.phuocpt98.demo.repository.RoleRepository;
import com.phuocpt98.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
     UserRepository userRepository;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;
     RoleRepository roleRepository;


    public UserResponse create(UserCreationRequest request) {
        log.info("Create user");
        if(userRepository.existsByFullName(request.getFull_name())){
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.BAD_REQUEST);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = userMapper.toUser(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow( () -> new AppException(ErrorCode.BAD_REQUEST));
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest){
        User user = getUserById(userId);

        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setEmail(userUpdateRequest.getEmail());
        user.setFullName(userUpdateRequest.getFull_name());

        var roles = roleRepository.findAllById(userUpdateRequest.getRole_ids());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITS));

        return userMapper.toUserResponse(user);
    }
}
