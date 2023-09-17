package briefing;

import briefing.base.BaseException;
import briefing.base.BaseExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ExceptionResponse> handleException(final BaseException e) {
    final BaseExceptionType type = e.exceptionType();
    log.warn("[WARN] MESSAGE: {}", type.message());
    return new ResponseEntity<>(ExceptionResponse.from(e), e.exceptionType().status());
  }

  private record ExceptionResponse(String message) {

    private static ExceptionResponse from(final BaseException e) {
      return new ExceptionResponse(e.exceptionType().message());
    }
  }
}
