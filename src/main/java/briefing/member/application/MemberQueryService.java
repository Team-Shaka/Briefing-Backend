package briefing.member.application;

import briefing.member.domain.Member;
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
}
