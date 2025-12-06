package com.phuocpt98.demo.dto.response;

import com.phuocpt98.demo.entity.Role;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


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
