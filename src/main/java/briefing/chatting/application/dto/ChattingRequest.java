package briefing.chatting.application.dto;

import java.util.List;

import briefing.chatting.domain.GptModel;
import briefing.chatting.domain.MessageRole;
import lombok.Getter;

public class ChattingRequest {

@Getter
public static class MessageRequestDTO {
	MessageRole role;
	String content;
}

@Getter
public static class AnswerRequestDTO {
	GptModel model;
	List<MessageRequestDTO> messages;
}
}
