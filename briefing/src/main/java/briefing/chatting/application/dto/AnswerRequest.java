package briefing.chatting.application.dto;

import briefing.chatting.domain.GptModel;
import java.util.List;
import java.util.Optional;

public record AnswerRequest(
    GptModel model,
    List<MessageRequest> messages
) {

  public Optional<MessageRequest> getLastMessage() {
    if (messages.isEmpty()) {
      return Optional.empty();
    }

    final int lastIndex = messages.size() - 1;
    return Optional.of(messages.get(lastIndex));
  }
}
