package briefing.briefing.exception;

import briefing.base.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BriefingExceptionType implements BaseExceptionType {
  NOT_FOUND_BRIEFING(HttpStatus.NOT_FOUND, "브리핑이 존재하지 않습니다."),
  NOT_FOUND_TYPE(HttpStatus.BAD_REQUEST, "해당하는 type을 찾을 수 없습니다.");

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
