package briefing.member.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import briefing.common.enums.MemberRole;
import briefing.common.enums.SocialType;
import briefing.common.exception.common.ErrorCode;
import briefing.member.domain.Member;
import briefing.member.domain.repository.MemberRepository;
import briefing.member.exception.MemberException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member testForTokenApi() {
        return memberRepository
                .findFirstByOrderByCreatedAt()
                .orElseGet(
                        () ->
                                memberRepository.save(
                                        Member.builder()
                                                .nickName(",,,!,1")
                                                .socialId("1234567")
                                                .socialType(SocialType.GOOGLE)
                                                .role(MemberRole.ROLE_USER)
                                                .build()));
    }
}
