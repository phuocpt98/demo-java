package com.phuocpt98.demo.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
public class UserCreationRequest {

    @NotEmpty(message = "INVALID_REQUEST")
    @Size(min = 3, max = 20, message = "IALID_REQUEST")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 5, message = "Email must be at least 5 characters")
    private String email;

    @NotEmpty(message = "Full name cannot be empty")
    @Size(min = 3, message = "Full name must be at least 3 characters")
    private String full_name;

}
