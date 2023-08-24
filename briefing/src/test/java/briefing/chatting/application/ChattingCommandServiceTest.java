package briefing.chatting.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import briefing.chatting.application.dto.ChattingCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(value = "/init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ChattingCommandServiceTest {

  @Autowired
  private ChattingCommandService chattingCommandService;

  @Test
  void createChattingTest() {
    //given
    final long expectId = 1L;

    //when
    final ChattingCreateResponse actual = chattingCommandService.createChatting();

    //then
    assertEquals(expectId, actual.id());
  }
}
