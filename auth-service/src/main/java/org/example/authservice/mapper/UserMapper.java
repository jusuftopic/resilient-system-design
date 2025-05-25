package org.example.authservice.mapper;

import org.example.authservice.dto.request.RegisterRequest;
import org.example.authservice.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", constant = "USER")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    public abstract UserInfo map(RegisterRequest request);
}
