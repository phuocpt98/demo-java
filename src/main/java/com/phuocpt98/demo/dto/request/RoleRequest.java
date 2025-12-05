package com.phuocpt98.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleRequest {

    private String description;

    private Set<Long> permission_id;
}
