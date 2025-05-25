package org.example.authservice.service;

import org.example.authservice.dto.request.LoginRequest;
import org.example.authservice.dto.request.RegisterRequest;
import org.example.authservice.dto.response.AuthenticationResponse;

public interface AuthService
{

    /**
     * Authenticates a user and returns an authentication response with tokens
     */
    AuthenticationResponse login(LoginRequest loginRequest);

    /**
     * Registers a new user and returns an authentication response with tokens
     */
    AuthenticationResponse register(RegisterRequest registerRequest);

    /**
     * Refreshes an authentication token using a valid refresh token
     */
    AuthenticationResponse refreshToken(String refreshToken);

    /**
     * Logs out a user by invalidating their refresh token
     */
    void logout(String refreshToken);
}
