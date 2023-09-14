package briefing.chatting.exception;

import briefing.base.BaseException;
import briefing.base.BaseExceptionType;

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
