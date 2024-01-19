package briefing.infra.redis.service;

import briefing.common.enums.SocialType;
import briefing.infra.redis.domain.RefreshToken;
import briefing.member.presentation.dto.MemberRequest;

public interface RedisService {

    RefreshToken generateRefreshToken(String socialId, SocialType socialType);

    // accessToken 만료 시 발급 혹은 그대로 반환
    RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request);

    void deleteRefreshToken(String refreshToken);
}
