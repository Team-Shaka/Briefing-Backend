package briefing.chatting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import briefing.SpringBootTestHelper;
import briefing.chatting.domain.ChattingRepository;
import briefing.chatting.service.dto.ChattingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ChattingCommandServiceTest extends SpringBootTestHelper {

  @Autowired
  private ChattingCommandService chattingCommandService;
  @Autowired
  private ChattingRepository chattingRepository;

  @Test
  @DisplayName("채팅을 생성한다.")
  void createChattingTest() {
    //when
    final ChattingResponse actual = chattingCommandService.createChatting();

    final ChattingResponse expect = ChattingResponse.from(
        chattingRepository.findById(actual.id()).get()
    );

    //then
    assertAll(
        () -> assertNotNull(expect),
        () -> assertNotNull(actual),
        () -> assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expect)
    );
  }
}
