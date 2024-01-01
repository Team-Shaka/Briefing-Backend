package briefing.scrap.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import briefing.common.enums.APIVersion;
import briefing.common.response.CommonResponse;
import briefing.scrap.application.ScrapCommandService;
import briefing.scrap.application.ScrapQueryService;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.application.dto.ScrapResponse;
import briefing.scrap.application.dto.ScrapV2;
import briefing.scrap.domain.Scrap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "05-Scrap V2 📁", description = "스크랩 관련 API V2")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class ScrapV2Api {
    private final ScrapQueryService scrapQueryService;
    private final ScrapCommandService scrapCommandService;

    @Operation(summary = "05-01 Scrap📁 스크랩하기 V2", description = "브리핑을 스크랩하는 API입니다.")
    @PostMapping("/scraps/briefings")
    public CommonResponse<ScrapResponse.CreateDTOV2> createV2(
            @RequestBody ScrapRequest.CreateDTO request) {
        Scrap createdScrap = scrapCommandService.create(request, APIVersion.V2);
        return CommonResponse.onSuccess(ScrapConverter.toCreateDTOV2((ScrapV2) createdScrap));
    }

    @Operation(summary = "05-02 Scrap📁 스크랩 취소 V2", description = "스크랩을 취소하는 API입니다.")
    @DeleteMapping("/scraps/briefings/{briefingId}/members/{memberId}")
    public CommonResponse<ScrapResponse.DeleteDTOV2> deleteV2(
            @PathVariable Long briefingId, @PathVariable Long memberId) {
        Scrap deletedScrap = scrapCommandService.delete(briefingId, memberId, APIVersion.V2);
        return CommonResponse.onSuccess(ScrapConverter.toDeleteDTOV2((ScrapV2) deletedScrap));
    }

    @Operation(summary = "05-03 Scrap📁 내 스크랩 조회 V2", description = "내 스크랩을 조회하는 API입니다.")
    @GetMapping("/scraps/briefings/members/{memberId}")
    public CommonResponse<List<ScrapResponse.ReadDTOV2>> getScrapsByMemberV2(
            @PathVariable Long memberId) {
        List<Scrap> scraps = scrapQueryService.getScrapsByMemberId(memberId);
        return CommonResponse.onSuccess(scraps.stream().map(ScrapConverter::toReadDTOV2).toList());
    }
}
