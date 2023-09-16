package briefing.member.api;

import briefing.member.application.MemberCommandService;
import briefing.member.application.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
}
