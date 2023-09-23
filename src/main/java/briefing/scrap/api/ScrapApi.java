package briefing.scrap.api;

import briefing.common.response.CommonResponse;
import briefing.scrap.application.ScrapCommandService;
import briefing.scrap.application.ScrapQueryService;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.application.dto.ScrapResponse;
import briefing.scrap.domain.Scrap;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "05-Scrap ",description = "사용자 관련 API")
@RestController
@RequestMapping("/scraps")
@RequiredArgsConstructor
public class ScrapApi {
    private final ScrapQueryService scrapQueryService;
    private final ScrapCommandService scrapCommandService;

    @PostMapping("/briefings")
    public CommonResponse<ScrapResponse.CreateDTO> create(@RequestBody ScrapRequest.CreateDTO request) {
        Scrap scrap = scrapCommandService.create(request);
        return CommonResponse.onSuccess(ScrapConverter.toCreateDTO(scrap));
    }
}
