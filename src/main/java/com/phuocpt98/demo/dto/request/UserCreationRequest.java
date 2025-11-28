package com.phuocpt98.demo.dto.request;


import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
public class UserCreationRequest {
    private String username;
    private String password;
    private String email;
    private String full_name;

}
