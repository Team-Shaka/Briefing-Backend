package briefing.chatting.application.dto;

import briefing.chatting.domain.Chatting;
import java.time.LocalDateTime;

public record ChattingCreateResponse(
    Long id,
    LocalDateTime createdAt
) {

  public static ChattingCreateResponse from(final Chatting chatting) {
    return new ChattingCreateResponse(
        chatting.getId(),
        chatting.getCreatedAt()
    );
  }
}
