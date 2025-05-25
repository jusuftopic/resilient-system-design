package org.example.authservice.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for refreshing authentication tokens.
 * Contains the refresh token used to obtain a new authentication token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest
{

    /**
     * The refresh token used to obtain a new authentication token.
     */
    @NotBlank(message = "Refresh token must not be blank")
    private String refreshToken;
}
