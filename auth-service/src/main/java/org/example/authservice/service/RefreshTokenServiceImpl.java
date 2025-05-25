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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public RefreshTokenDTO rotateToken(String token)
    {
        final RefreshToken existingToken = refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new IllegalStateException("Invalid refresh token"));

        validateToken(existingToken);
        existingToken.setRevoked(true);
        refreshTokenRepository.save(existingToken);

        return generateToken(existingToken.getUser().getEmail());
    }


    private void validateToken(RefreshToken token)
    {
        if (token.getExpiresAt().isBefore(Instant.now()))
        {
            refreshTokenRepository.deleteById(token.getId());
            throw new RuntimeException("Refresh token expired");
        }
        if (token.isRevoked())
        {
            refreshTokenRepository.deleteById(token.getId());
            throw new RuntimeException("Token is revoked expired");
        }

    }

    @Override
    public void deleteByToken(String token)
    {
        refreshTokenRepository.deleteByToken(token);
    }
}
