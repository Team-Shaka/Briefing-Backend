package briefing.chatting.api;

import briefing.chatting.application.ChattingCommandService;
import briefing.chatting.application.ChattingQueryService;
import briefing.chatting.application.dto.AnswerRequest;
import briefing.chatting.application.dto.AnswerResponse;
import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.application.dto.ChattingDetailResponse;
import briefing.chatting.application.dto.ChattingsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chattings")
@RequiredArgsConstructor
public class ChattingApi {

  private final ChattingQueryService chattingQueryService;
  private final ChattingCommandService chattingCommandService;

  @GetMapping
  public ChattingsResponse findChattings(@RequestParam final List<Long> ids) {
    return chattingQueryService.findChattings(ids);
  }

  @GetMapping("/{id}")
  public ChattingDetailResponse findChatting(@PathVariable final Long id) {
    return chattingQueryService.findChatting(id);
  }

  @PostMapping
  public ChattingCreateResponse createChatting() {
    return chattingCommandService.createChatting();
  }

  @PostMapping("/{id}")
  public AnswerResponse requestAnswer(
      @PathVariable final Long id,
      @RequestBody final AnswerRequest request
  ) {
    return chattingCommandService.requestAnswer(id, request);
  }
}
