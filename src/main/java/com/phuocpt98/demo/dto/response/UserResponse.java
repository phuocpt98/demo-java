package com.phuocpt98.demo.dto.response;

import java.util.Set;

import com.phuocpt98.demo.entity.Role;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

     Long id;

     String username;
     String email;
     String full_name;
    Set<Role> roles;
}
