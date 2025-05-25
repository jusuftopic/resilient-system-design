package org.example.authservice.service;

public interface AccessTokenService
{

    String generateToken(String subject);
}
