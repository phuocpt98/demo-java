package com.phuocpt98.demo.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntrospectResponse {
    private boolean active;
}
