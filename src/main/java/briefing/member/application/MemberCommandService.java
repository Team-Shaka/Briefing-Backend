package briefing.member.application;

import briefing.feign.oauth.google.client.GoogleOauth2Client;
import briefing.feign.oauth.google.dto.GoogleUserInfo;
import briefing.member.api.MemberConverter;
import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.Member;
import briefing.member.domain.MemberRole;
import briefing.member.domain.MemberStatus;
import briefing.member.domain.SocialType;
import briefing.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final GoogleOauth2Client googleOauth2Client;


    public Member login(SocialType socialType, MemberRequest.LoginDTO request) {
        return switch (socialType) {
            case GOOGLE -> loginWithGoogle(request);
            case APPLE -> loginWithApple(request);
        };
    }

    private Member loginWithGoogle(MemberRequest.LoginDTO request) {
        // 구글에서 사용자 정보 조회
        GoogleUserInfo googleUserInfo = googleOauth2Client.verifyToken(request.getIdentityToken());

        Member member = memberRepository.findBySocialIdAndSocialType(googleUserInfo.getSub(), SocialType.GOOGLE)
                .orElseGet(() -> MemberConverter.toMember(googleUserInfo));

        return memberRepository.save(member);

    }

    private Member loginWithApple(MemberRequest.LoginDTO request) {
        // 애플 로그인 로직 구현
        return null;
    }
}
