package briefing.member.application;

import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.Member;
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

    public Member login(SocialType socialType, MemberRequest.LoginDTO request) {
        return switch (socialType) {
            case GOOGLE -> loginWithGoogle(request);
            case APPLE -> loginWithApple(request);
        };
    }

    private Member loginWithGoogle(MemberRequest.LoginDTO request) {
        // 구글 로그인 로직 구현
        return null;
    }

    private Member loginWithApple(MemberRequest.LoginDTO request) {
        // 애플 로그인 로직 구현
        return null;
    }
}
