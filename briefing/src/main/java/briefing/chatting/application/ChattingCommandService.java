package briefing.chatting.application;

import briefing.chatting.application.dto.AnswerRequest;
import briefing.chatting.application.dto.AnswerResponse;
import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.application.dto.MessageRequest;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.Message;
import briefing.chatting.domain.repository.ChattingRepository;
import briefing.chatting.domain.repository.MessageRepository;
import java.util.List;
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

  public ChattingCreateResponse createChatting() {
    final Chatting chatting = chattingRepository.save(new Chatting());
    return ChattingCreateResponse.from(chatting);
  }

  public AnswerResponse requestAnswer(final Long id, final AnswerRequest request) {
    final Chatting chatting = chattingRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("채팅을 찾을 수 없습니다."));

    final MessageRequest lastMessage = request.getLastMessage()
        .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
    validateLastMessage(lastMessage);

    final Message question = new Message(chatting, lastMessage.role(), lastMessage.content());
    final Message answer = chatGptClient.requestAnswer(chatting, request);

    if (chatting.isNotInitialized()) {
      initChatting(request, chatting, question);
    }
    messageRepository.save(question);
    messageRepository.save(answer);

    return AnswerResponse.from(answer);
  }

  private void initChatting(final AnswerRequest request, final Chatting chatting,
      final Message question) {
    chatting.updateTitle(question.getContent());

    final List<Message> messages = request.getMessagesExcludeLast().stream()
        .map(message -> new Message(chatting, message.role(), message.content()))
        .toList();

    messageRepository.saveAll(messages);
  }

  private void validateLastMessage(final MessageRequest questionRequest) {
    if (questionRequest.role().isNotUser()) {
      throw new IllegalArgumentException("마지막 메시지가 사용자의 메시지가 아닙니다.");
    }
    if (questionRequest.isInvalidContent()) {
      throw new IllegalArgumentException("메시지가 비어있습니다.");
    }
  }
}
