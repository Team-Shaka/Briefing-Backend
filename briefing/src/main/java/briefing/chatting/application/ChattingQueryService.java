package briefing.chatting.application;

import briefing.chatting.application.dto.ChattingDetailResponse;
import briefing.chatting.application.dto.ChattingsResponse;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.repository.ChattingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChattingQueryService {

  private final ChattingRepository chattingRepository;

  public ChattingsResponse findChattings(final List<Long> ids) {
    final List<Chatting> chattings = chattingRepository.findAllById(ids);

    return ChattingsResponse.from(chattings);
  }

  public ChattingDetailResponse findChatting(final Long id) {
    final Chatting chatting = chattingRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("채팅을 찾을 수 없습니다."));

    return ChattingDetailResponse.from(chatting);
  }
}
