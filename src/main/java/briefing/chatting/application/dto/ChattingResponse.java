package briefing.chatting.application.dto;

import briefing.chatting.domain.MessageRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ChattingResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerResponseDTO{
        Long id;
        MessageRole role;
        String content;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageResponseDTO{
        Long id;
        MessageRole role;
        String content;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChattingDetailResponseDTO{
        Long id;
        String title;
        List<MessageResponseDTO> messages;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChattingResponseDTO{
        Long id;
        String title;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChattingListResponseDTO{
        List<ChattingResponseDTO> chattings;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChattingCreateResponseDTO{
        Long id;
        LocalDateTime createdAt;
    }
}
