package com.phuocpt98.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String password;
    private String email;
    private String full_name;
}
