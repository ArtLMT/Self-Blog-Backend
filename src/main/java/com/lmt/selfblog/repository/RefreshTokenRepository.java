package com.lmt.selfblog.repository;

import com.lmt.selfblog.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);

    RefreshToken findByUser_IdAndRevokedIsFalse(Long userId);
}
