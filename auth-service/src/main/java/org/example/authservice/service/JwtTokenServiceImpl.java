package org.example.authservice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

@Service
public class JwtTokenServiceImpl implements AccessTokenService
{
    private static final String SECRET = "";

    // 30 minutes expiration date
    private static final Date EXPIRATION_DATE = new Date(System.currentTimeMillis() + 1000 * 60 * 30);

    @Override
    public String generateToken(String subject)
    {
        return Jwts.builder()
            .claims(new HashMap<>())
            .subject(subject)
            .issuedAt(new Date())
            .expiration(EXPIRATION_DATE)
            .signWith(getSignKey())
            .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
