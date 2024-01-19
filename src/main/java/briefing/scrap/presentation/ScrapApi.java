package briefing.scrap.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import briefing.common.presentation.response.CommonResponse;
import briefing.scrap.business.ScrapFacade;
import briefing.scrap.presentation.dto.ScrapRequest;
import briefing.scrap.presentation.dto.ScrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "05-Scrap 📁", description = "스크랩 관련 API")
@RestController
@RequiredArgsConstructor
public class ScrapApi {
    private final ScrapFacade scrapFacade;

    @Operation(summary = "05-01 Scrap📁 스크랩하기 V1", description = "브리핑을 스크랩하는 API입니다.")
    @PostMapping("/scraps/briefings")
    public CommonResponse<ScrapResponse.CreateDTO> create(
            @RequestBody final ScrapRequest.CreateDTO request) {
        return CommonResponse.onSuccess(scrapFacade.create(request));
    }

    @Operation(summary = "05-02 Scrap📁 스크랩 취소 V1", description = "스크랩을 취소하는 API입니다.")
    @DeleteMapping("/scraps/briefings/{briefingId}/members/{memberId}")
    public CommonResponse<ScrapResponse.DeleteDTO> delete(
            @PathVariable final Long briefingId, @PathVariable final Long memberId) {
        return CommonResponse.onSuccess(scrapFacade.delete(briefingId, memberId));
    }

    @Operation(summary = "05-03 Scrap📁 내 스크랩 조회 V1", description = "내 스크랩을 조회하는 API입니다.")
    @GetMapping("/scraps/briefings/members/{memberId}")
    public CommonResponse<List<ScrapResponse.ReadDTO>> getScrapsByMember(
            @PathVariable final Long memberId) {
        return CommonResponse.onSuccess(scrapFacade.getScrapsByMemberId(memberId));
    }
}
