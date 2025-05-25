package org.example.authservice.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REFRESH_TOKEN")
public class RefreshToken
{
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @ManyToOne
    private UserInfo user;

    private Instant expiresAt;

    private boolean revoked;
}
