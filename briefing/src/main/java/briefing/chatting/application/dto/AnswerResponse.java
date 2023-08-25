package briefing.chatting.application.dto;

import briefing.chatting.domain.Message;
import briefing.chatting.domain.MessageRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record AnswerResponse(
    Long id,
    MessageRole role,
    String content,
    @JsonProperty("created_at") LocalDateTime createdAt
) {

  public static AnswerResponse from(final Message message) {
    return new AnswerResponse(
        message.getId(),
        message.getRole(),
        message.getContent(),
        message.getCreatedAt()
    );
  }
}
