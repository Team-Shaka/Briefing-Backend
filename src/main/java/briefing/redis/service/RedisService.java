package briefing.redis.service;

import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.Member;
import briefing.member.domain.SocialType;
import briefing.redis.domain.RefreshToken;

public interface RedisService {

    RefreshToken generateRefreshToken(String socialId, SocialType socialType);

    // accessToken 만료 시 발급 혹은 그대로 반환
    RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request);

    void deleteRefreshToken(String refreshToken);
}
