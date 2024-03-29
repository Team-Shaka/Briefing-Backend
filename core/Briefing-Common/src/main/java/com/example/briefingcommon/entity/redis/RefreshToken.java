package com.example.briefingcommon.entity.redis;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken_Breifing", timeToLive = 1800000)
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id private String token;

    private Long memberId;

    private LocalDateTime expireTime;
}
