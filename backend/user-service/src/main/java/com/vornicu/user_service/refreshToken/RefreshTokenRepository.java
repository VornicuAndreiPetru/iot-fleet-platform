package com.vornicu.user_service.refreshToken;

import com.vornicu.user_service.refreshToken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
