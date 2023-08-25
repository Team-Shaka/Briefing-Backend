package briefing.chatting.application;

import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.application.dto.MessageRequest;
import briefing.chatting.application.dto.MessageResponse;
import briefing.chatting.application.dto.QuestionMessageRequest;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.Message;
import briefing.chatting.domain.repository.ChattingRepository;
import briefing.chatting.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingCommandService {

  private final ChattingRepository chattingRepository;
  private final MessageRepository messageRepository;

  public ChattingCreateResponse createChatting() {
    final Chatting chatting = chattingRepository.save(new Chatting());
    return ChattingCreateResponse.from(chatting);
  }

  public MessageResponse createQuestion(final Long id, final QuestionMessageRequest request) {
    final Chatting chatting = chattingRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("채팅을 찾을 수 없습니다."));

    final MessageRequest questionRequest = request.getLastMessage()
        .orElseThrow(() -> new IllegalArgumentException("새로운 메시지를 찾을 수 없습니다."));
    validateQuestionMessage(questionRequest);

    final Message question = new Message(chatting, questionRequest.role(),
        questionRequest.content());

    if (chatting.isTitleUpdatable()) {
      chatting.updateTitle(question.getContent());
    }

    final Message message = messageRepository.save(question);

    return MessageResponse.from(message);
  }

  private void validateQuestionMessage(final MessageRequest questionRequest) {
    if (questionRequest.role().isNotUser()) {
      throw new IllegalArgumentException("마지막 메시지가 사용자의 메시지가 아닙니다.");
    }
    if (questionRequest.content().isBlank()) {
      throw new IllegalArgumentException("메시지가 비어있습니다.");
    }
  }
}
