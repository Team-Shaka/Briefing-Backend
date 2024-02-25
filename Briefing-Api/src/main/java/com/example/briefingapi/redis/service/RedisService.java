package com.example.briefingapi.redis.service;


import com.example.briefingapi.member.presentation.dto.MemberRequest;
import com.example.briefingcommon.entity.enums.SocialType;
import com.example.briefingcommon.entity.redis.RefreshToken;


public interface RedisService {

    RefreshToken generateRefreshToken(String socialId, SocialType socialType);

    String generateTestRefreshToken();

    // accessToken 만료 시 발급 혹은 그대로 반환
    RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request);

    void deleteRefreshToken(String refreshToken);
}
