package briefing.member.api;

import briefing.common.response.CommonResponse;
import briefing.member.application.MemberCommandService;
import briefing.member.application.MemberQueryService;
import briefing.member.application.dto.MemberRequest;
import briefing.member.application.dto.MemberResponse;
import briefing.member.domain.Member;
import briefing.member.domain.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @PostMapping("/auth/{socialType}")
    public CommonResponse<MemberResponse.LoginDTO> login(
        @PathVariable final SocialType socialType,
        @RequestBody final MemberRequest.LoginDTO request
    ) {
        Member member = memberCommandService.login(socialType, request);
        // TODO - TokenProvider에서 발급해주도록 변경
        String accessToken = "";
        String refreshToken = "";
        return CommonResponse.onSuccess(MemberConverter.toLoginDTO(member, accessToken, refreshToken));
    }
}
