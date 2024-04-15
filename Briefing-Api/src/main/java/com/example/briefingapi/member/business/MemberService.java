package com.example.briefingapi.member.business;

import java.util.List;

import com.example.briefingapi.fcm.implementation.FcmCommandService;
import com.example.briefingapi.member.implement.MemberCommandAdapter;
import com.example.briefingapi.member.implement.MemberQueryAdapter;
import com.example.briefingapi.member.presentation.dto.MemberRequest;
import com.example.briefingapi.member.presentation.dto.MemberResponse;
import com.example.briefingapi.security.provider.TokenProvider;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.MemberRole;
import com.example.briefingcommon.entity.enums.SocialType;
import com.example.briefingcommon.entity.redis.RefreshToken;
import com.example.briefinginfra.feign.oauth.apple.client.AppleOauth2Client;
import com.example.briefinginfra.feign.oauth.google.client.GoogleOauth2Client;
import com.example.briefinginfra.feign.oauth.google.dto.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.briefingapi.redis.service.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberQueryAdapter memberQueryAdapter;
    private final MemberCommandAdapter memberCommandAdapter;
    private final TokenProvider tokenProvider;
    private final FcmCommandService fcmCommandService;
    private final RedisService redisService;
    private final GoogleOauth2Client googleOauth2Client;
    private final AppleOauth2Client appleOauth2Client;

    @Value("${fcm.topic.daily-push}")
    private String dailyPushTopic;

    @Value(("${fcm.permit-flag}"))
    private Integer permitFlag;

    private Member loginWithGoogle(String identityToken) {
        GoogleUserInfo googleUserInfo = googleOauth2Client.verifyToken(identityToken);
        Member member =
                memberQueryAdapter
                        .findBySocialIdAndSocialType(googleUserInfo.getSub(), SocialType.GOOGLE)
                        .orElseGet(() -> MemberMapper.toMember(googleUserInfo));
        return memberCommandAdapter.save(member);
    }

    @Transactional
    public MemberResponse.LoginDTO login(
            final SocialType socialType, final MemberRequest.LoginDTO request) {

        Member member =
                socialType == SocialType.GOOGLE
                        ? loginWithGoogle(request.getIdentityToken())
                        : memberCommandAdapter.login(request);
        String accessToken =
                tokenProvider.createAccessToken(
                        member.getId(),
                        member.getSocialType().toString(),
                        member.getSocialId(),
                        List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name())));
        String refreshToken =
                redisService
                        .generateRefreshToken(member.getSocialId(), member.getSocialType())
                        .getToken();
        return MemberMapper.toLoginDTO(member, accessToken, refreshToken);
    }

    public MemberResponse.TestTokenDTO getTestToken(){
        Member member = memberQueryAdapter.findById(63L);

        return MemberResponse.TestTokenDTO.builder()
                .token(
                        tokenProvider.createAccessToken(
                        member.getId(),
                        member.getSocialType().toString(),
                        member.getSocialId(),
                        List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name()))))
                .refeshToken(redisService.generateTestRefreshToken())
                .build();
    }

    public MemberResponse.ReIssueTokenDTO reIssueToken(final MemberRequest.ReissueDTO request) {
        RefreshToken refreshToken = redisService.reGenerateRefreshToken(request);
        Member parsedMember = memberQueryAdapter.parseRefreshToken(refreshToken);
        String accessToken =
                tokenProvider.createAccessToken(
                        parsedMember.getId(),
                        parsedMember.getSocialType().toString(),
                        parsedMember.getSocialId(),
                        List.of(new SimpleGrantedAuthority(parsedMember.getRole().toString())));
        return MemberMapper.toReIssueTokenDTO(
                parsedMember.getId(), accessToken, refreshToken.getToken());
    }

    public MemberResponse.QuitDTO quit(final Long memberId) {
        memberCommandAdapter.deleteMember(memberId);
        return MemberMapper.toQuitDTO();
    }

    public void subScribeDailyPush(MemberRequest.ToggleDailyPushAlarmDTO request, Member member){

        if(request.getPermit().equals(permitFlag)){
            memberCommandAdapter.storeFcmToken(request.getFcmToken(),member);
            fcmCommandService.subScribe(dailyPushTopic, request.getFcmToken());
        }
        else {
            memberCommandAdapter.abortFcmToken(request.getFcmToken(), member);
            fcmCommandService.unSubScribe(dailyPushTopic,request.getFcmToken());
        }
    }
}
