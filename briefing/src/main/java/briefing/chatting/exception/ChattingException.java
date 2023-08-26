package briefing.chatting.exception;

import briefing.BaseException;
import briefing.BaseExceptionType;

public class ChattingException extends BaseException {

  private final ChattingExceptionType exceptionType;

  public ChattingException(final ChattingExceptionType exceptionType) {
    super(exceptionType.message());
    this.exceptionType = exceptionType;
  }

  @Override
  public BaseExceptionType exceptionType() {
    return exceptionType;
  }
}
