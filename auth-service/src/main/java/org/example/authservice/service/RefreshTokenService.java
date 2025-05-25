package org.example.authservice.service;

import java.util.Optional;

import org.example.authservice.dto.RefreshTokenDTO;

public interface RefreshTokenService
{

    RefreshTokenDTO generateToken(String email);

    Optional<RefreshTokenDTO> getByToken(String token);

    void validateToken(RefreshTokenDTO token);

    void deleteByToken(String token);

}
