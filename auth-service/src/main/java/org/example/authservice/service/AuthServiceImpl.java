package org.example.authservice.service;
;

import org.example.authservice.dto.RefreshTokenDTO;
import org.example.authservice.dto.request.LoginRequest;
import org.example.authservice.dto.request.RegisterRequest;
import org.example.authservice.dto.response.AuthenticationResponse;
import org.example.authservice.entity.UserInfo;
import org.example.authservice.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{

    private final AccessTokenService accessTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest)
    {
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        if (authentication.isAuthenticated())
        {
            return new AuthenticationResponse(
                accessTokenService.generateToken(loginRequest.getEmail()),
                refreshTokenService.generateToken(loginRequest.getEmail()).getToken()
            );
        }
        else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest)
    {
        if (userService.getByEmail(registerRequest.getEmail()).isPresent())
        {
            throw new IllegalStateException("Email already in use");
        }

        final UserInfo saved = userService.save(userMapper.map(registerRequest));
        return new AuthenticationResponse(
            accessTokenService.generateToken(saved.getEmail()),
            refreshTokenService.generateToken(saved.getEmail()).getToken());
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken)
    {

        final RefreshTokenDTO newRefreshToken = refreshTokenService.rotateToken(refreshToken);
        final String newAccessToken = accessTokenService.generateToken(newRefreshToken.getUser().getEmail());

        return new AuthenticationResponse(newAccessToken, newRefreshToken.getToken());
    }

    @Override
    public void logout(String refreshToken)
    {
        refreshTokenService.deleteByToken(refreshToken);
    }
}
