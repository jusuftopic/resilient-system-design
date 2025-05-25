package org.example.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest
{
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;

    @NotBlank(message = "Firstname must not be blank")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank")
    private String lastName;
}
