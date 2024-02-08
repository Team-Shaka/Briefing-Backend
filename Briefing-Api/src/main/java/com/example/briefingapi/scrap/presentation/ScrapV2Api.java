package com.example.briefingapi.scrap.presentation;
import java.util.List;

import com.example.briefingapi.scrap.business.ScrapV2Facade;
import com.example.briefingapi.scrap.presentation.dto.ScrapRequest;
import com.example.briefingapi.scrap.presentation.dto.ScrapResponse;
import com.example.briefingcommon.common.presentation.response.CommonResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import com.example.briefingapi.aop.annotation.CacheEvictByBriefingId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "05-Scrap V2 📁", description = "스크랩 관련 API V2")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class ScrapV2Api {
    private final ScrapV2Facade scrapFacade;

    @CacheEvictByBriefingId(value = "findBriefingsV2", briefingId = "#request.getBriefingId()")
    @Operation(summary = "05-01 Scrap📁 스크랩하기 V2", description = "브리핑을 스크랩하는 API입니다.")
    @PostMapping("/scraps/briefings")
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
    })
    public CommonResponse<ScrapResponse.CreateDTOV2> createV2(
            @RequestBody final ScrapRequest.CreateDTO request) {
        return CommonResponse.onSuccess(scrapFacade.create(request));
    }

    @CacheEvictByBriefingId(value = "findBriefingsV2", briefingId = "#briefingId")
    @Operation(summary = "05-02 Scrap📁 스크랩 취소 V2", description = "스크랩을 취소하는 API입니다.")
    @DeleteMapping("/scraps/briefings/{briefingId}/members/{memberId}")
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
    })
    public CommonResponse<ScrapResponse.DeleteDTOV2> deleteV2(
            @PathVariable final Long briefingId, @PathVariable final Long memberId) {
        return CommonResponse.onSuccess(scrapFacade.delete(briefingId, memberId));
    }

    @Operation(summary = "05-03 Scrap📁 내 스크랩 조회 V2", description = "내 스크랩을 조회하는 API입니다.")
    @GetMapping("/scraps/briefings/members/{memberId}")
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
    })
    public CommonResponse<List<ScrapResponse.ReadDTOV2>> getScrapsByMemberV2(
            @PathVariable final Long memberId) {
        return CommonResponse.onSuccess(scrapFacade.getScrapsByMemberId(memberId));
    }
}
