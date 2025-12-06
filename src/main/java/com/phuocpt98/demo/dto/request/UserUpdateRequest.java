package com.phuocpt98.demo.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String password;
    private String email;
    private String full_name;
    private List<Long> role_ids;
}
