package briefing.chatting.application.dto;

import briefing.chatting.domain.Chatting;
import java.util.List;

public record ChattingDetailResponse(
    Long id,
    String title,
    List<MessageResponse> messages
) {

  public static ChattingDetailResponse from(final Chatting chatting) {
    final List<MessageResponse> messageResponses = chatting.getMessages().stream()
        .map(MessageResponse::from)
        .toList();

    return new ChattingDetailResponse(
        chatting.getId(),
        chatting.getTitle(),
        messageResponses
    );
  }
}
