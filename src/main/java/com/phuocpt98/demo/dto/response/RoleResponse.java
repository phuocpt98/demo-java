package com.phuocpt98.demo.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponse {

    private Long id;
    private String description;
    private Set<PermissionResponse> permissions;
}
