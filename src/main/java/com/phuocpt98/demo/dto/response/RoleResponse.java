package com.phuocpt98.demo.dto.response;


import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {

    private Long id;
    private String description;
    private Set<PermissionResponse> permissions;
}
