package briefing.member.api;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import briefing.common.response.CommonResponse;
import briefing.member.application.MemberCommandService;
import briefing.member.application.MemberQueryService;
import briefing.member.application.dto.MemberRequest;
import briefing.member.application.dto.MemberResponse;
import briefing.member.domain.Member;
import briefing.member.domain.MemberRole;
import briefing.member.domain.SocialType;
import briefing.redis.domain.RefreshToken;
import briefing.redis.service.RedisService;
import briefing.security.handler.annotation.AuthMember;
import briefing.security.provider.TokenProvider;
import briefing.validation.annotation.CheckSameMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "02-Member V2 \uD83D\uDC64", description = "사용자 관련 API V2")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses({
    @ApiResponse(
            responseCode = "COMMON000",
            description = "SERVER ERROR, 백앤드 개발자에게 알려주세요",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
})
@RequestMapping("/v2")
public class MemberV2Api {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    private final TokenProvider tokenProvider;

    private final RedisService redisService;

    @Operation(summary = "02-01 Member\uD83D\uDC64 소셜 로그인 V2", description = "구글, 애플 소셜로그인 API입니다.")
    @PostMapping("/members/auth/{socialType}")
    public CommonResponse<MemberResponse.LoginDTO> loginV2(
            @Parameter(description = "소셜로그인 종류", example = "google") @PathVariable
                    final SocialType socialType,
            @RequestBody final MemberRequest.LoginDTO request) {
        Member member = memberCommandService.login(socialType, request);
        String accessToken =
                tokenProvider.createAccessToken(
                        member.getId(),
                        member.getSocialType().toString(),
                        member.getSocialId(),
                        List.of(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name())));
        String refreshToken =
                redisService
                        .generateRefreshToken(member.getSocialId(), member.getSocialType())
                        .getToken();
        return CommonResponse.onSuccess(
                MemberConverter.toLoginDTO(member, accessToken, refreshToken));
    }

    @Operation(
            summary = "02-01 Member\uD83D\uDC64 accessToken 재발급 받기 V2",
            description = "accessToken 만료 시 refreshToken으로 재발급을 받는 API 입니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "1000", description = "OK, 성공"),
        @ApiResponse(
                responseCode = "COMMON001",
                description = "request body에 담길 값이 이상함, result를 확인해주세요!",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
                responseCode = "AUTH005",
                description = "리프레시 토큰도 만료, 다시 로그인",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
                responseCode = "AUTH009",
                description = "리프레시 토큰 모양이 잘못 됨",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    @PostMapping("/members/auth/token")
    public CommonResponse<MemberResponse.ReIssueTokenDTO> reissueTokenV2(
            @Valid @RequestBody MemberRequest.ReissueDTO request) {
        RefreshToken refreshToken = redisService.reGenerateRefreshToken(request);
        Member parsedMember = memberCommandService.parseRefreshToken(refreshToken);
        String accessToken =
                tokenProvider.createAccessToken(
                        parsedMember.getId(),
                        parsedMember.getSocialType().toString(),
                        parsedMember.getSocialId(),
                        List.of(new SimpleGrantedAuthority(parsedMember.getRole().toString())));
        return CommonResponse.onSuccess(
                MemberConverter.toReIssueTokenDTO(
                        parsedMember.getId(), accessToken, refreshToken.getToken()));
    }

    @Operation(summary = "02-01 Member\uD83D\uDC64 회원 탈퇴 V2", description = "회원 탈퇴 API 입니다.")
    @DeleteMapping("/members/{memberId}")
    @Parameters({
        @Parameter(name = "member", hidden = true),
        @Parameter(name = "memberId", description = "삭제 대상 멤버아이디")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "1000", description = "OK, 성공"),
        @ApiResponse(
                responseCode = "AUTH003",
                description = "access 토큰을 주세요!",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
                responseCode = "AUTH004",
                description = "acess 토큰 만료",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
                responseCode = "AUTH006",
                description = "acess 토큰 모양이 이상함",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
                responseCode = "MEMBER_001",
                description = "사용자가 존재하지 않습니다.",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
                responseCode = "MEMBER_002",
                description = "로그인 한 사용자와 탈퇴 대상이 동일하지 않습니다.",
                content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    })
    public CommonResponse<MemberResponse.QuitDTO> quitMemberV2(
            @AuthMember Member member, @CheckSameMember @PathVariable Long memberId) {
        memberCommandService.deleteMember(memberId);
        return CommonResponse.onSuccess(MemberConverter.toQuitDTO());
    }
}
