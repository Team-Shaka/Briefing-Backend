package briefing.chatting.application.dto;

import briefing.chatting.domain.Chatting;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ChattingResponse(
    Long id,
    String title,
    @JsonProperty("created_at") LocalDateTime createdAt
) {

  public static ChattingResponse from(final Chatting chatting) {
    return new ChattingResponse(
        chatting.getId(),
        chatting.getTitle(),
        chatting.getCreatedAt()
    );
  }
}
