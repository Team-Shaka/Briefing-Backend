package briefing.chatting.application.dto;

import briefing.chatting.domain.GptModel;
import briefing.chatting.domain.MessageRole;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class ChattingRequest {

    @Getter
    public static class MessageRequestDTO{
        MessageRole role;
        String content;
    }

    @Getter
    public static class AnswerRequestDTO{
        GptModel model;
        List<MessageRequestDTO> messages;
    }
}