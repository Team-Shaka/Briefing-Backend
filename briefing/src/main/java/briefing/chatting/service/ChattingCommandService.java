package briefing.chatting.service;

import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.ChattingRepository;
import briefing.chatting.service.dto.ChattingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingCommandService {

  private final ChattingRepository chattingRepository;

  public ChattingResponse createChatting() {
    return ChattingResponse.from(chattingRepository.save(new Chatting()));
  }
}
