package briefing.member.api;

import briefing.member.application.dto.MemberResponse;
import briefing.member.domain.Member;

public class MemberConverter {

    public static MemberResponse.LoginDTO toLoginDTO(Member member, String accessToken, String refreshToken) {
        return MemberResponse.LoginDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
