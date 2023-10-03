package briefing.member.application;

import briefing.member.domain.Member;
import briefing.member.domain.MemberRole;
import briefing.member.domain.SocialType;
import briefing.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public Member findMember(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public Member testForTokenApi(){
        return memberRepository.findFirstByOrderByCreatedAt().orElseGet(()->
                memberRepository.save(Member.builder().nickName(",,,!,1").socialId("1234567").socialType(SocialType.GOOGLE).role(MemberRole.ROLE_USER)
                        .build())
        );
    }
}
