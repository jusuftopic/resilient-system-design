package org.example.authservice.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.example.authservice.dto.RefreshTokenDTO;
import org.example.authservice.entity.RefreshToken;
import org.example.authservice.entity.UserInfo;
import org.example.authservice.mapper.RefreshTokenMapper;
import org.example.authservice.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService
{
   // Expiration time for refresh tokens is set to 7 days
    private static Long refreshTokenExpirationMs = 7L * 24 * 60 * 60 * 1000;;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserService userService;

    @Override
    public RefreshTokenDTO generateToken(String email)
    {
        final UserInfo user = userService.getByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        final RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setExpiresAt(Instant.now().plusMillis(refreshTokenExpirationMs));
        token.setToken(UUID.randomUUID().toString());

        return refreshTokenMapper.map(
            refreshTokenRepository.save(token)
        );
    }

    @Override
    public Optional<RefreshTokenDTO> getByToken(String token)
    {
        return refreshTokenRepository.findByToken(token)
            .map(refreshTokenMapper::map);
    }

    @Override
    public void validateToken(RefreshTokenDTO token)
    {
        if (token.getExpiresAt().isBefore(Instant.now()))
        {
            refreshTokenRepository.deleteById(token.getId());
            throw new RuntimeException("Refresh token expired");
        }
    }

    @Override
    public void deleteByToken(String token)
    {
        refreshTokenRepository.deleteByToken(token);
    }
}
