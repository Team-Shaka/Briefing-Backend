package briefing.redis.service;

import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.repository.MemberRepository;
import briefing.redis.domain.RefreshToken;
import briefing.redis.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisServiceImpl implements RedisService{

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken(String email) {
        return null;
    }

    @Override
    public RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request) {
        return null;
    }
}
