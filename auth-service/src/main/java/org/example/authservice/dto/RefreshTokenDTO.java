package org.example.authservice.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO
{
    private Long id;
    private String token;
    private UserDTO user;
    private Instant expiresAt;
    private boolean revoked;

    /**
     * Returns the user associated with this refresh token.
     *
     * @return the UserDTO associated with this token
     */
    public UserDTO getUser()
    {
       if (user == null)
       {
           throw new IllegalStateException("Token needs to be associated with a user");
       }

       return user;
    }
}
