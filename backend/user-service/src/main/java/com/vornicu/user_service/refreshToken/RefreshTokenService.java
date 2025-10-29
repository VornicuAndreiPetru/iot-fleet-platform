package com.vornicu.user_service.refreshToken;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String,String> redisTemplate;
    private final Duration refreshTokenExpiration = Duration.ofDays(7);

    public String generateRefreshToken(Integer userId){
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
          token,
          userId.toString(),
          refreshTokenExpiration
        );

        return token;
    }

    public Integer validateRefreshToken(String token){
        String userId = redisTemplate.opsForValue().get(token);
        if(userId == null){
            throw new RuntimeException("Refresh token invalid or expired!");
        }
        return Integer.valueOf(userId);
    }

    public void deleteRefreshToken(String token){
        redisTemplate.delete(token);
    }
}
