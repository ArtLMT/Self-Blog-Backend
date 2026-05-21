package com.lmt.selfblog.repository;

import com.lmt.selfblog.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    RefreshToken findByToken(String token);

    RefreshToken findByUser_IdAndRevokedIsFalse(UUID userId);
}
