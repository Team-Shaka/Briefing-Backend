package briefing.chatting.exception;

import briefing.base.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ChattingExceptionType implements BaseExceptionType {
  NOT_FOUND_CHATTING(HttpStatus.NOT_FOUND, "해당하는 채팅을 찾을 수 없습니다."),
  LAST_MESSAGE_NOT_EXIST(HttpStatus.BAD_REQUEST, "마지막 메시지를 찾을 수 없습니다."),
  BAD_LAST_MESSAGE_ROLE(HttpStatus.BAD_REQUEST, "마지막 메시지의 역할이 user가 아닙니다."),
  CAN_NOT_EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "content가 비어있습니다."),
  NOT_FOUND_ROLE(HttpStatus.BAD_REQUEST, "해당하는 role을 찾을 수 없습니다."),
  NOT_FOUND_MODEL(HttpStatus.BAD_REQUEST, "해당하는 mode을 찾을 수 없습니다.");

  private final HttpStatus status;
  private final String message;

  @Override
  public HttpStatus status() {
    return status;
  }

  @Override
  public String message() {
    return message;
  }
}
