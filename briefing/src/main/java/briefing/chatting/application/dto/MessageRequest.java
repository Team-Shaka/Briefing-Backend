package briefing.chatting.application.dto;

import briefing.chatting.domain.MessageRole;

public record MessageRequest(
    MessageRole role,
    String content
) {

}
