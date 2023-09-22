package briefing.redis.service;

import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.SocialType;
import briefing.redis.domain.RefreshToken;

public interface RedisService {

    String generateRefreshToken(String socialId, SocialType socialType);

    // accessToken 만료 시 발급 혹은 그대로 반환
    String reGenerateRefreshToken(MemberRequest.ReissueDTO request);

    void deleteRefreshToken(String refreshToken);
}
