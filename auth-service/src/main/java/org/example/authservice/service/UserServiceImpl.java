package org.example.authservice.service;

import java.util.Optional;

import org.example.authservice.entity.UserInfo;
import org.example.authservice.mapper.UserMapper;
import org.example.authservice.repository.UserInfoRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserInfoRepository userInfoRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        return userInfoRepository.findByEmail(email)
            .map(UserInfoDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public Optional<UserInfo> getByEmail(String email)
    {
        return userInfoRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserInfo save(UserInfo user)
    {
        return userInfoRepository.save(user);
    }
}
