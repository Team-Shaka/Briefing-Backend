package briefing.chatting.application.dto;

import briefing.chatting.domain.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record MessageResponse(
    Long id,
    String role,
    String content,
    @JsonProperty("created_at") LocalDateTime createdAt
) {

  public static MessageResponse from(final Message message) {
    return new MessageResponse(
        message.getId(),
        message.getRole().getValue(),
        message.getContent(),
        message.getCreatedAt()
    );
  }
}
