package org.example.authservice.repository;

import java.util.Optional;

import org.example.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>
{

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query(value = "DELETE FROM RefreshToken t  WHERE t.token = :token", nativeQuery = true)
    void deleteByToken(@Param("token") String token);
}
