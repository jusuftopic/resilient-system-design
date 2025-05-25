package org.example.authservice.service;

import java.util.Optional;

import org.example.authservice.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{

    Optional<UserInfo> getByEmail(String email);

    UserInfo save(UserInfo user);
}
