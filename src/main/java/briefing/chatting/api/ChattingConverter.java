package briefing.chatting.api;

import briefing.chatting.application.dto.ChattingRequest;
import briefing.chatting.application.dto.ChattingResponse;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.Message;

import java.util.List;
import java.util.Optional;

public class ChattingConverter {

    public static ChattingResponse.AnswerResponseDTO toAnswerResponseDTO(final Message message){
        return ChattingResponse.AnswerResponseDTO.builder()
                .id(message.getId())
                .role(message.getRole())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public static ChattingResponse.MessageResponseDTO toMessageResponseDTO(final Message message){
        return ChattingResponse.MessageResponseDTO.builder()
                .id(message.getId())
                .role(message.getRole())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public static ChattingResponse.ChattingDetailResponseDTO toChattingDetailResponseDTO(Chatting chatting){
        List<ChattingResponse.MessageResponseDTO> messageResponseDTOList = chatting.getMessages().stream()
                .map(ChattingConverter::toMessageResponseDTO).toList();
        return ChattingResponse.ChattingDetailResponseDTO.builder()
                .id(chatting.getId())
                .title(chatting.getTitle())
                .build();
    }

    public static ChattingResponse.ChattingResponseDTO toChattingResponseDTO(Chatting chatting){
        return ChattingResponse.ChattingResponseDTO.builder()
                .id(chatting.getId())
                .title(chatting.getTitle())
                .createdAt(chatting.getCreatedAt())
                .build();
    }

    public static ChattingResponse.ChattingListResponseDTO toChattingListResponseDTO(final List<Chatting>chattingList){

        List<ChattingResponse.ChattingResponseDTO> chattingResponseDTOList = chattingList.stream()
                .map(ChattingConverter::toChattingResponseDTO).toList();
        return ChattingResponse.ChattingListResponseDTO.builder()
                .chattings(chattingResponseDTOList)
                .build();
    }

    public static ChattingResponse.ChattingCreateResponseDTO toChattingCreateResponseDTO(Chatting chatting){
        return ChattingResponse.ChattingCreateResponseDTO.builder()
                .id(chatting.getId())
                .createdAt(chatting.getCreatedAt())
                .build();
    }

    public static List<ChattingRequest.MessageRequestDTO> getMessagesExcludeLast(List<ChattingRequest.MessageRequestDTO> messages){
        return messages.subList(0, messages.size() - 1);
    }

    public static Optional<ChattingRequest.MessageRequestDTO> getLastMessage(List<ChattingRequest.MessageRequestDTO> messages){
        if(messages.isEmpty())
            return Optional.empty();
        final int lastIndex = messages.size() - 1;
        return Optional.of(messages.get(lastIndex));
    }
}
