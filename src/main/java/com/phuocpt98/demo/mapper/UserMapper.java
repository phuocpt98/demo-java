package com.phuocpt98.demo.mapper;


import com.phuocpt98.demo.dto.request.UserCreationRequest;
import com.phuocpt98.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", source = "full_name")
    User toUser(UserCreationRequest userCreationRequest);
}
