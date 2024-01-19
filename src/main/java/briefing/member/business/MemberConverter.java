package briefing.member.business;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import briefing.common.enums.MemberRole;
import briefing.common.enums.MemberStatus;
import briefing.common.enums.SocialType;
import briefing.infra.feign.oauth.google.dto.GoogleUserInfo;
import briefing.member.domain.Member;
import briefing.member.presentation.dto.MemberResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberConverter {

    public static MemberResponse.LoginDTO toLoginDTO(
            Member member, String accessToken, String refreshToken) {
        return MemberResponse.LoginDTO.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static Member toMember(GoogleUserInfo googleUserInfo) {
        return Member.builder()
                .profileImgUrl(googleUserInfo.getPicture())
                .nickName(googleUserInfo.getName())
                .socialId(googleUserInfo.getSub())
                .socialType(SocialType.GOOGLE)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public static Member toMember(String appleSocialId) {
        return Member.builder()
                //                .profileImgUrl(googleUserInfo.getPicture())
                //                .nickName(googleUserInfo.getName())
                .socialId(appleSocialId)
                .socialType(SocialType.APPLE)
                .role(MemberRole.ROLE_USER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public static MemberResponse.ReIssueTokenDTO toReIssueTokenDTO(
            Long memberId, String accessToken, String refreshToken) {
        return MemberResponse.ReIssueTokenDTO.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static MemberResponse.QuitDTO toQuitDTO() {
        return MemberResponse.QuitDTO.builder().quitAt(LocalDateTime.now()).build();
    }
}
