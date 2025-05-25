package org.example.authservice.service;
;

import org.example.authservice.dto.RefreshTokenDTO;
import org.example.authservice.dto.request.LoginRequest;
import org.example.authservice.dto.request.RegisterRequest;
import org.example.authservice.dto.response.AuthenticationResponse;
import org.example.authservice.entity.UserInfo;
import org.example.authservice.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        validateRegistrationInput(loginRequest.getEmail(), loginRequest.getPassword());

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
            throw new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail());
        }
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest)
    {
        validateRegistrationInput(registerRequest.getEmail(), registerRequest.getPassword());
        if (userService.getByEmail(registerRequest.getEmail()).isPresent())
        {
            throw new IllegalStateException("User already exists with email: " + registerRequest.getEmail());
        }

        final UserInfo saved = userService.save(userMapper.map(registerRequest));
        return new AuthenticationResponse(
            accessTokenService.generateToken(saved.getEmail()),
            refreshTokenService.generateToken(saved.getEmail()).getToken());
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken)
    {
        final RefreshTokenDTO newRefreshToken = refreshTokenService.getByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenService.validateToken(newRefreshToken);

        final String newAccessToken = accessTokenService.generateToken(newRefreshToken.getUser().getEmail());

        return new AuthenticationResponse(newAccessToken, newRefreshToken.getToken());
    }

    @Override
    public void logout(String refreshToken)
    {
        refreshTokenService.deleteByToken(refreshToken);
    }

    private void validateRegistrationInput(final String email, final String password)
    {
        if (email == null || email.isBlank() || password == null || password.isBlank())
        {
            throw new IllegalArgumentException("Email and password must be set");
        }

        // Validate proper email format
        if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))
        {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Validate password strength
        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") ||
            !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"))
        {
            throw new IllegalArgumentException("Password must be at least 8 characters long, include an uppercase letter, "
                + "a lowercase letter, a number, and a special character");
        }
    }
}
