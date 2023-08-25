package briefing.chatting.application.dto;

import briefing.chatting.domain.Message;
import briefing.chatting.domain.MessageRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record MessageResponse(
    Long id,
    MessageRole role,
    String content,
    @JsonProperty("created_at") LocalDateTime createdAt
) {

  public static MessageResponse from(final Message message) {
    return new MessageResponse(
        message.getId(),
        message.getRole(),
        message.getContent(),
        message.getCreatedAt()
    );
  }
}
