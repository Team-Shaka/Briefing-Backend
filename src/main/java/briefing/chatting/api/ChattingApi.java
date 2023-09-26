package briefing.chatting.api;

import briefing.chatting.application.ChattingCommandService;
import briefing.chatting.application.ChattingQueryService;
import briefing.chatting.application.dto.*;

import java.util.List;

import briefing.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "04-Chatting \uD83D\uDCE8",description = "채팅 관련 API")
@RestController
@RequestMapping("/chattings")
@RequiredArgsConstructor
public class ChattingApi {

  private final ChattingQueryService chattingQueryService;
  private final ChattingCommandService chattingCommandService;

  @GetMapping
  public CommonResponse<ChattingResponse.ChattingListResponseDTO> findChattings(@RequestParam final List<Long> ids) {
    return CommonResponse.onSuccess(ChattingConverter.toChattingListResponseDTO(chattingQueryService.findChattings(ids)));
  }

  @GetMapping("/{id}")
  public CommonResponse<ChattingResponse.ChattingDetailResponseDTO> findChatting(@PathVariable final Long id) {
    return CommonResponse.onSuccess(ChattingConverter.toChattingDetailResponseDTO(chattingQueryService.findChatting(id)));
  }

  @PostMapping
  public CommonResponse<ChattingResponse.ChattingCreateResponseDTO> createChatting() {
    return CommonResponse.onSuccess(ChattingConverter.toChattingCreateResponseDTO(chattingCommandService.createChatting()));
  }

  @PostMapping("/{id}")
  public CommonResponse<ChattingResponse.AnswerResponseDTO> requestAnswer(
      @PathVariable final Long id,
      @RequestBody final ChattingRequest.AnswerRequestDTO request
  ) {
    return CommonResponse.onSuccess(ChattingConverter.toAnswerResponseDTO(chattingCommandService.requestAnswer(id, request)));
  }
}
