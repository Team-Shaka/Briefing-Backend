package briefing.briefing.api;

import briefing.briefing.service.BriefingQueryService;
import briefing.briefing.service.dto.BriefingDetailResponse;
import briefing.briefing.service.dto.BriefingsResponse;
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
      @RequestParam("type") final String type,
      @RequestParam("date") final LocalDate date
  ) {
    return briefingQueryService.findBriefings(type, date);
  }

  @GetMapping("/{id}")
  public BriefingDetailResponse findBriefing(@PathVariable("id") final Long id) {
    return briefingQueryService.findBriefing(id);
  }
}
