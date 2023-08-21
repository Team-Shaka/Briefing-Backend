package briefing.chatting.service.dto;

import briefing.chatting.domain.Chatting;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ChattingResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("created_at") LocalDateTime createdAt
) {

  public static ChattingResponse from(final Chatting chatting) {
    return new ChattingResponse(chatting.getId(), chatting.getCreatedAt());
  }
}
