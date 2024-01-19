package briefing.member.business;

import org.springframework.stereotype.Component;

import briefing.member.implement.MemberCommandService;
import briefing.member.implement.MemberQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
}
