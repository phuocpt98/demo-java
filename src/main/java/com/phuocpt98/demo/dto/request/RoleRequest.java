package com.phuocpt98.demo.dto.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

    private String description;

    private Set<Long> permission_id;
}
