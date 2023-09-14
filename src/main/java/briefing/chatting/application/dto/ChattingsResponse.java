package briefing.chatting.application.dto;

import briefing.chatting.domain.Chatting;
import java.util.List;

public record ChattingsResponse(
    List<ChattingResponse> chattings
) {

  public static ChattingsResponse from(final List<Chatting> chattings) {
    final List<ChattingResponse> chattingResponses = chattings.stream()
        .map(ChattingResponse::from)
        .toList();

    return new ChattingsResponse(chattingResponses);
  }
}
