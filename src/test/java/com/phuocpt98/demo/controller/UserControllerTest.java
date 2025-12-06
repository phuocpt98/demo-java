package com.phuocpt98.demo.controller;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.dto.response.UserResponse;
import com.phuocpt98.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/test.properties")
public class UserControllerTest {


    private UserController userController;
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserCreationRequest userCreationRequest;

    private UserResponse userResponse;

    @BeforeEach
    void initData(){
         userCreationRequest = UserCreationRequest.builder().username("test").password("123456").email("test@gmail.com").full_name("test full name").build();

         userResponse = UserResponse.builder().id(123L).username("test").email("test@gmail.com").full_name("test full name").build();
    }

    @Test
    void createUser_validate_success() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userCreationRequest);


        Mockito.when(
          userService.create(Mockito.any())).thenReturn(userResponse);


        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(123));

    }

}
