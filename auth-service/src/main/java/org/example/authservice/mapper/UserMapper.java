package org.example.authservice.mapper;

import org.example.authservice.dto.UserDTO;
import org.example.authservice.dto.request.RegisterRequest;
import org.example.authservice.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", constant = "USER")
    UserInfo map(RegisterRequest request);

    UserDTO map(UserInfo userInfo);
}
