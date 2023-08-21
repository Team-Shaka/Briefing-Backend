package briefing.chatting.api;

import briefing.chatting.service.ChattingCommandService;
import briefing.chatting.service.dto.ChattingResponse;
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
  public ChattingResponse createChatting() {
    return chattingCommandService.createChatting();
  }
}
