package briefing.redis.service;

import briefing.member.application.dto.MemberRequest;
import briefing.redis.domain.RefreshToken;

public interface RedisService {

    RefreshToken generateRefreshToken(String email);

    // accessToken 만료 시 발급 혹은 그대로 반환
    RefreshToken reGenerateRefreshToken(MemberRequest.ReissueDTO request);
}
