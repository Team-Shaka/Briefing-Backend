package briefing.chatting.api;

import briefing.chatting.application.ChattingCommandService;
import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.application.dto.MessageResponse;
import briefing.chatting.application.dto.QuestionMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chattings")
@RequiredArgsConstructor
public class ChattingApi {

  private final ChattingCommandService chattingCommandService;

  @PostMapping
  public ChattingCreateResponse createChatting() {
    return chattingCommandService.createChatting();
  }

  @PostMapping("/{id}")
  public MessageResponse createQuestionMessage(
      @PathVariable final Long id,
      @RequestBody final QuestionMessageRequest request
  ) {
    return chattingCommandService.createQuestion(id, request);
  }
}
