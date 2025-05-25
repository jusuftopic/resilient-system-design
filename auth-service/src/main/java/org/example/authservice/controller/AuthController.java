package org.example.authservice.controller;


import java.util.Objects;

import jakarta.validation.Valid;

import org.example.authservice.dto.request.LoginRequest;
import org.example.authservice.dto.request.RefreshTokenRequest;
import org.example.authservice.dto.request.RegisterRequest;
import org.example.authservice.dto.response.AuthenticationResponse;
import org.example.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling authentication-related operations.
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;

    /**
     * Handles user login requests.
     *
     * @param loginRequest the login request containing user credentials
     * @return a response entity containing the authentication response
     */
     @PostMapping("/login")
     public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
         Objects.requireNonNull(loginRequest, "loginRequest must not be null");
         return ResponseEntity.ok(authService.login(loginRequest));
     }

    /**
     * Handles user registration requests.
     *
     * @param registerRequest the registration request containing user details
     * @return a response entity containing the authentication response
     */
     @PostMapping("/register")
     public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
         Objects.requireNonNull(registerRequest, "registerRequest must not be null");
         return ResponseEntity.ok(authService.register(registerRequest));
     }

    /**
     * Handles requests to refresh authentication tokens.
     *
     * @param refreshToken the refresh token to generate a new authentication token
     * @return a response entity containing the new authentication response
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
        @RequestBody @Valid RefreshTokenRequest refreshToken) {
        Objects.requireNonNull(refreshToken, "refreshToken must not be null");
        return ResponseEntity.ok(authService.refreshToken(refreshToken.getRefreshToken()));
    }


    /**
     * Handles user logout requests.
     *
     * @param refreshToken the refresh token to invalidate during logout
     * @return a response entity indicating successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshTokenRequest refreshToken) {
        Objects.requireNonNull(refreshToken, "refreshToken must not be null");
        authService.logout(refreshToken.getRefreshToken());
        return ResponseEntity.ok().build();
    }

}
