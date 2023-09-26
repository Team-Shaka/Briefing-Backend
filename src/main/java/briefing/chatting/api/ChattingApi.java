package briefing.chatting.api;

import briefing.chatting.application.ChattingCommandService;
import briefing.chatting.application.ChattingQueryService;
import briefing.chatting.application.dto.AnswerRequest;
import briefing.chatting.application.dto.AnswerResponse;
import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.application.dto.ChattingDetailResponse;
import briefing.chatting.application.dto.ChattingsResponse;
import java.util.List;

import briefing.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "04-Chatting \uD83D\uDCE8",description = "채팅 관련 API")
@RestController
@RequestMapping("/chattings")
@RequiredArgsConstructor
public class ChattingApi {

  private final ChattingQueryService chattingQueryService;
  private final ChattingCommandService chattingCommandService;

  @GetMapping
  public CommonResponse<ChattingsResponse> findChattings(@RequestParam final List<Long> ids) {
    return CommonResponse.onSuccess(chattingQueryService.findChattings(ids));
  }

  @GetMapping("/{id}")
  public CommonResponse<ChattingDetailResponse> findChatting(@PathVariable final Long id) {
    return CommonResponse.onSuccess(chattingQueryService.findChatting(id));
  }

  @PostMapping
  public CommonResponse<ChattingCreateResponse> createChatting() {
    return CommonResponse.onSuccess(chattingCommandService.createChatting());
  }

  @PostMapping("/{id}")
  public CommonResponse<AnswerResponse> requestAnswer(
      @PathVariable final Long id,
      @RequestBody final AnswerRequest request
  ) {
    return CommonResponse.onSuccess(chattingCommandService.requestAnswer(id, request));
  }
}
