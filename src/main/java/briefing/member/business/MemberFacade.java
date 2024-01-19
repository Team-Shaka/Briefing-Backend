package briefing.member.business;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import briefing.common.enums.MemberRole;
import briefing.common.enums.SocialType;
import briefing.infra.feign.oauth.apple.client.AppleOauth2Client;
import briefing.infra.feign.oauth.google.client.GoogleOauth2Client;
import briefing.infra.feign.oauth.google.dto.GoogleUserInfo;
import briefing.infra.redis.domain.RefreshToken;
import briefing.infra.redis.service.RedisService;
import briefing.member.domain.Member;
import briefing.member.implement.MemberCommandService;
import briefing.member.implement.MemberQueryService;
import briefing.member.presentation.dto.MemberRequest;
import briefing.member.presentation.dto.MemberResponse;
import briefing.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final GoogleOauth2Client googleOauth2Client;
    private final AppleOauth2Client appleOauth2Client;

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
}
