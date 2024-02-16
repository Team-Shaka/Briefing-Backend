package com.example.briefingapi.redis.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import com.example.briefingcommon.domain.repository.member.MemberRepository;
import com.example.briefingapi.member.presentation.dto.MemberRequest;
import com.example.briefingapi.redis.repository.RefreshTokenRepository;
import com.example.briefingcommon.common.exception.MemberException;
import com.example.briefingcommon.common.exception.RefreshTokenException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.SocialType;
import com.example.briefingcommon.entity.redis.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisServiceImpl implements RedisService {

    Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public RefreshToken generateRefreshToken(String socialId, SocialType socialType) {
        Member member =
                memberRepository
                        .findBySocialIdAndSocialType(socialId, socialType)
                        .orElseThrow(() -> new RefreshTokenException(ErrorCode.MEMBER_NOT_FOUND));

        // 이 부분 괜찮은지 리뷰
        String token = UUID.randomUUID().toString();

        Long memberId = member.getId();

        LocalDateTime currentTime = LocalDateTime.now();

        LocalDateTime expireTime = currentTime.plus(90, ChronoUnit.SECONDS);

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(memberId)
                        .token(token)
                        .expireTime(expireTime)
                        .build());
    }

    @Override
    public RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request) {
        if (request.getRefreshToken() == null)
            throw new MemberException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        RefreshToken findRefreshToken =
                refreshTokenRepository
                        .findById(request.getRefreshToken())
                        .orElseThrow(
                                () -> new RefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN));
        LocalDateTime expireTime = findRefreshToken.getExpireTime();
        LocalDateTime current = LocalDateTime.now();

        Member member =
                memberRepository
                        .findById(findRefreshToken.getMemberId())
                        .orElseThrow(() -> new RefreshTokenException(ErrorCode.MEMBER_NOT_FOUND));

        if (current.isAfter(expireTime)) {
            logger.error("이미 만료된 리프레시 토큰 발견");
            throw new RefreshTokenException(ErrorCode.RELOGIN_EXCEPTION);
        }
        else{
            logger.info("리프레시 토큰과 access 토큰 재발급");
            deleteRefreshToken(request.getRefreshToken());
            return generateRefreshToken(member.getSocialId(), member.getSocialType());
        }
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        Optional<RefreshToken> target = refreshTokenRepository.findById(refreshToken);
        target.ifPresent(refreshTokenRepository::delete);
    }
}
