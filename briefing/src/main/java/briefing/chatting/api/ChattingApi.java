package briefing.chatting.api;

import briefing.chatting.application.ChattingCommandService;
import briefing.chatting.application.dto.ChattingCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
}
