package briefing.redis.service;

import briefing.exception.ErrorCode;
import briefing.exception.handler.MemberException;
import briefing.exception.handler.RefreshTokenException;
import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.Member;
import briefing.member.domain.SocialType;
import briefing.member.domain.repository.MemberRepository;
import briefing.redis.domain.RefreshToken;
import briefing.redis.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisServiceImpl implements RedisService{

    Logger logger =LoggerFactory.getLogger(RedisServiceImpl.class);

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public RefreshToken generateRefreshToken(String socialId, SocialType socialType) {
        Member member = memberRepository.findBySocialIdAndSocialType(socialId, socialType).orElseThrow(() -> new RefreshTokenException(ErrorCode.MEMBER_NOT_FOUND));

        // 이 부분 괜찮은지 리뷰
        String token = UUID.randomUUID().toString();

        Long memberId = member.getId();

        LocalDateTime currentTime =LocalDateTime.now();

        LocalDateTime expireTime = currentTime.plus(1000, ChronoUnit.MINUTES);

        return refreshTokenRepository.save(
            RefreshToken.builder()
                    .memberId(memberId)
                    .token(token)
                    .expireTime(expireTime).build()
        );
    }

    @Override
    public RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request) {
        if(request.getRefreshToken() == null)
            throw new MemberException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        RefreshToken findRefreshToken = refreshTokenRepository.findById(request.getRefreshToken()).orElseThrow(() -> new RefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN));
        LocalDateTime expireTime = findRefreshToken.getExpireTime();
        LocalDateTime current = LocalDateTime.now();
        // 테스트용, 실제로는 현재 시간 + accessToken 만료 시간
        LocalDateTime expireDeadLine = current.plusSeconds(20);

        Member member = memberRepository.findById(findRefreshToken.getMemberId()).orElseThrow(() -> new RefreshTokenException(ErrorCode.MEMBER_NOT_FOUND));

        if(current.isAfter(expireTime)) {
            logger.error("이미 만료된 리프레시 토큰 발견");
            throw new RefreshTokenException(ErrorCode.RELOGIN_EXCEPTION);
        }

        // 새로 발급할 accessToken보다 refreshToken이 먼저 만료 될 경우인가?
        if(expireTime.isAfter(expireDeadLine)) {
            logger.info("기존 리프레시 토큰 발급");
            return findRefreshToken;
        }
        else {
            logger.info("accessToken보다 먼저 만료될 예정인 리프레시 토큰 발견");
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
