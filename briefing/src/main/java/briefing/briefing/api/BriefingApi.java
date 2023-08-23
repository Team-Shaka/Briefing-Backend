package briefing.briefing.api;

import briefing.briefing.application.BriefingQueryService;
import briefing.briefing.application.dto.BriefingDetailResponse;
import briefing.briefing.application.dto.BriefingsResponse;
import briefing.briefing.domain.BriefingType;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/briefings")
@RequiredArgsConstructor
public class BriefingApi {

  private final BriefingQueryService briefingQueryService;

  @GetMapping
  public BriefingsResponse findBriefings(
      @RequestParam("type") final BriefingType type,
      @RequestParam("date") final LocalDate date
  ) {
    return briefingQueryService.findBriefings(type, date);
  }

  @GetMapping("/{id}")
  public BriefingDetailResponse findBriefing(@PathVariable final Long id) {
    return briefingQueryService.findBriefing(id);
  }
}
