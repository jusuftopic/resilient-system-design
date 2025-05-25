package org.example.authservice.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.example.authservice.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoDetails implements UserDetails
{
    private final String username; // Changed from 'name' to 'email' for clarity
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(final UserInfo userInfo) {
        this.username = userInfo.getEmail(); // Assuming 'email' is the username
        this.password = userInfo.getPassword();
        this.authorities = Stream.of(userInfo.getRoles().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }
}
