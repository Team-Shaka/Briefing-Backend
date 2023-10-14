package briefing.member.api;

import briefing.common.response.CommonResponse;
import briefing.member.application.MemberCommandService;
import briefing.member.application.MemberQueryService;
import briefing.member.application.dto.MemberRequest;
import briefing.member.application.dto.MemberResponse;
import briefing.member.domain.Member;
import briefing.member.domain.SocialType;
import briefing.redis.domain.RefreshToken;
import briefing.redis.service.RedisService;
import briefing.security.handler.annotation.AuthMember;
import briefing.security.provider.TokenProvider;
import briefing.validation.annotation.CheckSameMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Ref;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Tag(name = "02-Member \uD83D\uDC64",description = "사용자 관련 API")
@RestController
@Validated
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    private final TokenProvider tokenProvider;

    private final RedisService redisService;

    @Operation(summary = "Member\uD83D\uDC64 토큰 잘 발급되나 테스트용API", description = "테스트 용")
    @GetMapping("/auth/test")
    public CommonResponse<MemberResponse.LoginDTO> testGenerateToken(){
        Member member = memberQueryService.testForTokenApi();
        String accessToken = tokenProvider.createAccessToken(member.getId(), member.getSocialType().toString() ,member.getSocialId(), Arrays.asList(new SimpleGrantedAuthority(member.getRole().name())));
        RefreshToken refreshToken = redisService.generateRefreshToken(member.getSocialId(), member.getSocialType());
        return CommonResponse.onSuccess(MemberConverter.toLoginDTO(member,accessToken, refreshToken.getToken()));
    }

    @Operation(summary = "02-01 Member\uD83D\uDC64 소셜 로그인 #FRAME", description = "구글, 애플 소셜로그인 API입니다.")
    @PostMapping("/auth/{socialType}")
    public CommonResponse<MemberResponse.LoginDTO> login(
        @Parameter(description = "소셜로그인 종류", example = "google")  @PathVariable final SocialType socialType,
        @RequestBody final MemberRequest.LoginDTO request
    ) {
        Member member = memberCommandService.login(socialType, request);
        // TODO - TokenProvider에서 발급해주도록 변경
        String accessToken = tokenProvider.createAccessToken(member.getId(),member.getSocialType().toString() ,member.getSocialId(), Arrays.asList(new SimpleGrantedAuthority("USER")));
        String refreshToken = redisService.generateRefreshToken(member.getSocialId(),member.getSocialType()).getToken();
        return CommonResponse.onSuccess(MemberConverter.toLoginDTO(member, accessToken, refreshToken));
    }

    @PostMapping("/auth/token")
    public CommonResponse<MemberResponse.ReIssueTokenDTO> reissueToken(@Valid @RequestBody MemberRequest.ReissueDTO request){
        RefreshToken refreshToken = redisService.reGenerateRefreshToken(request);
        Member parsedMember = memberCommandService.parseRefreshToken(refreshToken);
        String accessToken = tokenProvider.createAccessToken(parsedMember.getId(),parsedMember.getSocialType().toString(), parsedMember.getSocialId(), List.of(new SimpleGrantedAuthority(parsedMember.getRole().toString())));
        return CommonResponse.onSuccess(MemberConverter.toReIssueTokenDTO(parsedMember.getId(), accessToken,refreshToken.getToken()));
    }


    @DeleteMapping("/{memberId}")
    @Parameters({
            @Parameter(name = "member", hidden = true),
            @Parameter(name = "memberId", description = "삭제 대상 멤버아이디")
    })
    public CommonResponse<MemberResponse.QuitDTO> quitMember(@AuthMember Member member, @CheckSameMember @PathVariable Long memberId){
        memberCommandService.deleteMember(memberId);
        return CommonResponse.onSuccess(MemberConverter.toQuitDTO());
    }
}
