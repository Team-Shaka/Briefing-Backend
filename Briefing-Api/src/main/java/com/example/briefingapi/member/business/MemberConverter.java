package com.example.briefingapi.member.business;

import java.time.LocalDateTime;

import com.example.briefingapi.member.presentation.dto.MemberResponse;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.MemberRole;
import com.example.briefingcommon.entity.enums.MemberStatus;
import com.example.briefingcommon.entity.enums.SocialType;
import com.example.briefinginfra.feign.oauth.google.dto.GoogleUserInfo;
import org.springframework.stereotype.Component;


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

    public static Member toMember(String appleSocialId, String nickName) {
        return Member.builder()
                //                .profileImgUrl(googleUserInfo.getPicture())
                //                .nickName(googleUserInfo.getName())
                .socialId(appleSocialId)
                .socialType(SocialType.APPLE)
                .nickName(nickName)
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
