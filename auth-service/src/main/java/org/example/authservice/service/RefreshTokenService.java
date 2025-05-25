package org.example.authservice.service;


import org.example.authservice.dto.RefreshTokenDTO;

public interface RefreshTokenService
{

    RefreshTokenDTO generateToken(String email);

    RefreshTokenDTO rotateToken(String token);

    void deleteByToken(String token);

}
