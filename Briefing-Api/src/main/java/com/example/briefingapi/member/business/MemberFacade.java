package com.example.briefingapi.member.business;

import java.util.List;

import com.example.briefingapi.fcm.implementation.FcmCommandService;
import com.example.briefingapi.member.implement.MemberCommandService;
import com.example.briefingapi.member.implement.MemberQueryService;
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
import org.springframework.transaction.annotation.Transactional;


import com.example.briefingapi.redis.service.RedisService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final TokenProvider tokenProvider;
    private final FcmCommandService fcmCommandService;
    private final RedisService redisService;
    private final GoogleOauth2Client googleOauth2Client;
    private final AppleOauth2Client appleOauth2Client;

    @Value("${fcm.topic.daily-push}")
    private String dailyPushTopic;

    private Member loginWithGoogle(String identityToken) {
        GoogleUserInfo googleUserInfo = googleOauth2Client.verifyToken(identityToken);
        Member member =
                memberQueryService
                        .findBySocialIdAndSocialType(googleUserInfo.getSub(), SocialType.GOOGLE)
                        .orElseGet(() -> MemberConverter.toMember(googleUserInfo));
        return memberCommandService.save(member);
    }

    @Transactional
    public MemberResponse.LoginDTO login(
            final SocialType socialType, final MemberRequest.LoginDTO request) {

        Member member =
                socialType == SocialType.GOOGLE
                        ? loginWithGoogle(request.getIdentityToken())
                        : memberCommandService.login(request);
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
        return MemberConverter.toLoginDTO(member, accessToken, refreshToken);
    }

    public MemberResponse.TestTokenDTO getTestToken(){
        Member member = memberQueryService.findById(63L);

        return MemberResponse.TestTokenDTO.builder()
                .token(
                        tokenProvider.createAccessToken(
                        member.getId(),
                        member.getSocialType().toString(),
                        member.getSocialId(),
                        List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name()))))
                .build();

    }

    public MemberResponse.ReIssueTokenDTO reIssueToken(final MemberRequest.ReissueDTO request) {
        RefreshToken refreshToken = redisService.reGenerateRefreshToken(request);
        Member parsedMember = memberQueryService.parseRefreshToken(refreshToken);
        String accessToken =
                tokenProvider.createAccessToken(
                        parsedMember.getId(),
                        parsedMember.getSocialType().toString(),
                        parsedMember.getSocialId(),
                        List.of(new SimpleGrantedAuthority(parsedMember.getRole().toString())));
        return MemberConverter.toReIssueTokenDTO(
                parsedMember.getId(), accessToken, refreshToken.getToken());
    }

    public MemberResponse.QuitDTO quit(final Long memberId) {
        memberCommandService.deleteMember(memberId);
        return MemberConverter.toQuitDTO();
    }

    public void subScribeDailyPush(MemberRequest.ToggleDailyPushAlarmDTO request, Member member){

        if(request.getPermit().equals(1)){
            memberCommandService.storeFcmToken(request.getFcmToken(),member);
            fcmCommandService.subScribe(dailyPushTopic, request.getFcmToken());
        }
        else {
            memberCommandService.abortFcmToken(request.getFcmToken(), member);
            fcmCommandService.unSubScribe(dailyPushTopic,request.getFcmToken());
        }
    }
}
