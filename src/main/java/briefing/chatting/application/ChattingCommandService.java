package briefing.chatting.application;

import briefing.chatting.api.ChattingConverter;
import briefing.chatting.application.dto.*;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.Message;
import briefing.chatting.domain.repository.ChattingRepository;
import briefing.chatting.domain.repository.MessageRepository;
import java.util.List;

import briefing.exception.ErrorCode;
import briefing.exception.handler.ChattingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingCommandService {

  private final ChattingRepository chattingRepository;
  private final MessageRepository messageRepository;
  private final ChatGptClient chatGptClient;

  public Chatting createChatting() {
    final Chatting chatting = chattingRepository.save(new Chatting());
    return chatting;
  }

  public Message requestAnswer(final Long id, final ChattingRequest.AnswerRequestDTO request) {
    final Chatting chatting = chattingRepository.findById(id)
        .orElseThrow(() -> new ChattingException(ErrorCode.NOT_FOUND_CHATTING));

    final ChattingRequest.MessageRequestDTO lastMessage = ChattingConverter.getLastMessage(request.getMessages())
        .orElseThrow(() -> new ChattingException(ErrorCode.LAST_MESSAGE_NOT_EXIST));
    validateLastMessage(lastMessage);

    final Message question = Message.builder()
            .chatting(chatting)
            .role(lastMessage.getRole())
            .content(lastMessage.getContent())
            .build();
    final Message answer = chatGptClient.requestAnswer(chatting, request);

    if (chatting.isNotInitialized()) {
      initChatting(request, chatting, question);
    }
    messageRepository.save(question);
    messageRepository.save(answer);

    return answer;
  }

  private void initChatting(final ChattingRequest.AnswerRequestDTO request, final Chatting chatting,
                            final Message question) {
    chatting.updateTitle(question.getContent());

    final List<Message> messages = ChattingConverter.getMessagesExcludeLast(request.getMessages()).stream()
        .map(
                message -> Message.builder()
                        .chatting(chatting)
                        .role(message.getRole())
                        .content(message.getContent())
                        .build()
            )
        .toList();

    messageRepository.saveAll(messages);
  }

  private void validateLastMessage(final ChattingRequest.MessageRequestDTO questionRequest) {
    if (questionRequest.getRole().isNotUser()) {
      throw new ChattingException(ErrorCode.BAD_LAST_MESSAGE_ROLE);
    }
    if (isInvalidContent(questionRequest)) {
      throw new ChattingException(ErrorCode.CAN_NOT_EMPTY_CONTENT);
    }
  }

  private boolean isInvalidContent(final ChattingRequest.MessageRequestDTO message) {
    return message.getContent() == null || message.getContent().isBlank();
  }
}
