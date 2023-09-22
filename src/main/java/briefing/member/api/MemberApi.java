package briefing.member.api;

import briefing.common.response.CommonResponse;
import briefing.member.application.MemberCommandService;
import briefing.member.application.MemberQueryService;
import briefing.member.application.dto.MemberRequest;
import briefing.member.application.dto.MemberResponse;
import briefing.member.domain.Member;
import briefing.member.domain.SocialType;
import briefing.redis.service.RedisService;
import briefing.security.provider.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@Tag(name = "02-Member \uD83D\uDC64",description = "사용자 관련 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    private final TokenProvider tokenProvider;

    private final RedisService redisService;

    @Operation(summary = "02-01 Member\uD83D\uDC64 소셜 로그인 #FRAME", description = "구글, 애플 소셜로그인 API입니다.")
    @PostMapping("/auth/{socialType}")
    public CommonResponse<MemberResponse.LoginDTO> login(
        @Parameter(description = "소셜로그인 종류", example = "google") @PathVariable final SocialType socialType,
        @RequestBody final MemberRequest.LoginDTO request
    ) {
        Member member = memberCommandService.login(socialType, request);
        // TODO - TokenProvider에서 발급해주도록 변경
        String accessToken = tokenProvider.createAccessToken(member.getId(),member.getSocialType().toString() ,member.getSocialId(), Arrays.asList(new SimpleGrantedAuthority("USER")));
        String refreshToken = redisService.generateRefreshToken(member.getSocialId(),member.getSocialType());
        return CommonResponse.onSuccess(MemberConverter.toLoginDTO(member, accessToken, refreshToken));
    }

    @Operation(summary = "토큰 잘 발급되나 테스트용API", description = "테스트 후 삭제합니다")
    @GetMapping("/auth/test")
    public CommonResponse<MemberResponse.LoginDTO> testGenerateToken(){
        Member member = memberQueryService.findMember(1L);
        String accessToken = tokenProvider.createAccessToken(member.getId(), member.getSocialType().toString() ,member.getSocialId(), Arrays.asList(new SimpleGrantedAuthority(member.getRole().name())));
        String refreshToken = redisService.generateRefreshToken(member.getSocialId(), member.getSocialType());
        return CommonResponse.onSuccess(MemberConverter.toLoginDTO(member,accessToken, refreshToken));
    }
}
