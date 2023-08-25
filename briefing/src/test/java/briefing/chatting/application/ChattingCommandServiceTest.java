package briefing.chatting.application;

import static briefing.chatting.domain.GptModel.GPT_3_5_TURBO;
import static briefing.chatting.domain.MessageRole.ASSISTANT;
import static briefing.chatting.domain.MessageRole.SYSTEM;
import static briefing.chatting.domain.MessageRole.USER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import briefing.chatting.application.dto.AnswerRequest;
import briefing.chatting.application.dto.AnswerResponse;
import briefing.chatting.application.dto.ChattingCreateResponse;
import briefing.chatting.application.dto.MessageRequest;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.Message;
import briefing.chatting.domain.MessageRole;
import briefing.chatting.domain.repository.ChattingRepository;
import briefing.chatting.domain.repository.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(value = "/init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ChattingCommandServiceTest {

  @Autowired
  private ChattingCommandService chattingCommandService;
  @Autowired
  private ChattingRepository chattingRepository;
  @Autowired
  private MessageRepository messageRepository;

  @MockBean
  private ChatGptClient chatGptClient;

  @Test
  void createChattingTest() {
    //given
    final ChattingCreateResponse expect = chattingCommandService.createChatting();

    //when
    final ChattingCreateResponse actual = ChattingCreateResponse.from(
        chattingRepository.findById(expect.id()).get()
    );

    //then
    assertNotNull(actual);
    assertEquals(expect.id(), actual.id());
  }

  @Nested
  @DisplayName("질문 저장 및 대답 요청 테스트")
  class RequestAnswer {

    private long chattingId;
    private Chatting chatting;
    private AnswerRequest request;
    private List<MessageRequest> messages;

    @BeforeEach
    void setUp() {
      chatting = chattingRepository.save(new Chatting());
      chattingId = chatting.getId();

      messages = List.of(
          new MessageRequest(
              SYSTEM,
              "You are News briefier. User will ask you some questions, then you have to say the answer with given context. You only can say truth, and only can say korean. thsi is given context"
          ),
          new MessageRequest(
              ASSISTANT,
              "Brief는 어제의 이슈에 대해서 뉴스 등의 기사를 통해 정보를 제공합니다. 해당 내용은 100% 신뢰할 수 없는 내용일 수 있으며, 높은 신뢰도를 위해서는 추천 기사 등을 통해 정보를 확인하시기 바랍니다. 어떤 것이 궁금하신가요?"
          ),
          new MessageRequest(
              USER,
              "잼버리가 뭐야?"
          )
      );
      request = new AnswerRequest(GPT_3_5_TURBO, messages);
    }

    @Test
    @DisplayName("채팅의 질문과 대답을 정상적으로 저장한다.")
    void requestAnswerTest() {
      //given
      final MessageRole expectRole = ASSISTANT;
      final String expectContent = "예상 대답";

      when(chatGptClient.requestAnswer(any(), any()))
          .thenReturn(new Message(chatting, expectRole, expectContent));

      //when
      final AnswerResponse expect = chattingCommandService.requestAnswer(chattingId, request);
      final AnswerResponse actual = AnswerResponse.from(
          messageRepository.findById(expect.id()).get());

      //then
      assertAll(
          () -> assertEquals(expect.id(), actual.id()),
          () -> assertEquals(expect.role(), actual.role()),
          () -> assertEquals(expect.content(), actual.content())
      );
    }

    @Test
    @DisplayName("채팅이 존재하지 않을 경우 Exception이 발생한다.")
    void requestAnswerWithNotExistChattingTest() {
      //given
      final long notExistChattingId = 0L;

      //when & then
      final IllegalArgumentException exception = assertThrowsExactly(
          IllegalArgumentException.class,
          () -> chattingCommandService.requestAnswer(notExistChattingId, request)
      );

      assertEquals("채팅을 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("채팅이 존재하지 않을 경우 Exception이 발생한다.")
    void requestAnswerWithEmptyMessagesTest() {
      //given
      final List<MessageRequest> emptyMessages = new ArrayList<>();
      final AnswerRequest emptyMessageRequest = new AnswerRequest(GPT_3_5_TURBO, emptyMessages);

      //when & then
      final IllegalArgumentException exception = assertThrowsExactly(
          IllegalArgumentException.class,
          () -> chattingCommandService.requestAnswer(chattingId, emptyMessageRequest)
      );

      assertEquals("메시지를 찾을 수 없습니다.", exception.getMessage());
    }

    @ParameterizedTest
    @EnumSource(value = MessageRole.class, names = {"SYSTEM", "ASSISTANT"})
    @DisplayName("마지막 메시지(질문)의 role이 user가 아닐 경우 Exception이 발생한다.")
    void requestAnswerWithLastMessageRoleNotUserTest(final MessageRole role) {
      //given
      final List<MessageRequest> messages = new ArrayList<>(this.messages);
      messages.add(new MessageRequest(role, "잘못된 role의 질문"));

      final AnswerRequest wrongRequest = new AnswerRequest(GPT_3_5_TURBO, messages);

      //when & then
      final IllegalArgumentException exception = assertThrowsExactly(
          IllegalArgumentException.class,
          () -> chattingCommandService.requestAnswer(chattingId, wrongRequest)
      );

      assertEquals("마지막 메시지가 사용자의 메시지가 아닙니다.", exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "\n", "\t"})
    @DisplayName("마지막 메시지(질문)의 content가 비어있을 경우 Exception이 발생한다.")
    void requestAnswerWithEmptyContentTest(final String content) {
      //given
      final List<MessageRequest> messages = new ArrayList<>(this.messages);
      messages.add(new MessageRequest(USER, content));
      final AnswerRequest emptyMessageRequest = new AnswerRequest(GPT_3_5_TURBO, messages);

      //when & then
      final IllegalArgumentException exception = assertThrowsExactly(
          IllegalArgumentException.class,
          () -> chattingCommandService.requestAnswer(chattingId, emptyMessageRequest)
      );

      assertEquals("메시지가 비어있습니다.", exception.getMessage());
    }
  }
}
