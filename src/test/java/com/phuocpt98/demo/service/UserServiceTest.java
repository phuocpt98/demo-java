package com.phuocpt98.demo.service;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.response.UserResponse;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.exception.AppException;
import com.phuocpt98.demo.exception.ErrorCode;
import com.phuocpt98.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;


    private UserCreationRequest userCreationRequest;

    private UserResponse userResponse;

    private User user;

    @BeforeEach
    void initData(){
        userCreationRequest = UserCreationRequest.builder().username("test").password("123456").email("test@gmail.com").full_name("test full name").build();

        userResponse = UserResponse.builder().id(123L).username("test").email("test@gmail.com").full_name("test full name").build();

        user = User.builder()
                .id(123L).username("test").email("test@gmail.com").fullName("test full name").password("123456")
                .build();
    }


    @Test
    void createUser_validate_success(){
        // Given
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(userRepository.existsByUsername(Mockito.any())).thenReturn(false);
        Mockito.when(userRepository.existsByFullName(Mockito.any())).thenReturn(false);


        // when
        var response = userService.create(userCreationRequest);


        // then

        Assertions.assertEquals(userResponse, response);
    }


    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void getMyInfo_validate_success(){
        // Given
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(user));


        // when
        var response = userService.getMyInfo();
        // then
        Assertions.assertEquals(userResponse, response);
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void getMyI_notFound_validate_fail(){
        // Given
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.ofNullable(null));


        // when
        var exception = Assertions.assertThrows(
                AppException.class, () ->userService.getMyInfo()
        );
        // then
        Assertions.assertEquals(ErrorCode.USER_NOT_EXITS, exception.getErrorCode());
    }
}
