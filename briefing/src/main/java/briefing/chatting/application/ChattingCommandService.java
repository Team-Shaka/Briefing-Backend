package briefing.chatting.application;

import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.repository.ChattingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingCommandService {

  private final ChattingRepository chattingRepository;

  public ChattingCreateResponse createChatting() {
    final Chatting chatting = chattingRepository.save(new Chatting());
    return ChattingCreateResponse.from(chatting);
  }
}
