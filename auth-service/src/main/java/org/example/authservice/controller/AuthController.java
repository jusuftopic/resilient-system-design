package org.example.authservice.controller;


import java.util.Objects;

import org.example.authservice.dto.request.LoginRequest;
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
     public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
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
     public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
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
        @RequestBody String refreshToken) {
        Objects.requireNonNull(refreshToken, "refreshToken must not be null");
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


    /**
     * Handles user logout requests.
     *
     * @param refreshToken the refresh token to invalidate during logout
     * @return a response entity indicating successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        Objects.requireNonNull(refreshToken, "refreshToken must not be null");
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

}
