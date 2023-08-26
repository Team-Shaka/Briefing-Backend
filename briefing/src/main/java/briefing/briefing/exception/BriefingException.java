package briefing.briefing.exception;

import briefing.base.BaseException;
import briefing.base.BaseExceptionType;

public class BriefingException extends BaseException {

  private final BriefingExceptionType exceptionType;

  public BriefingException(final BriefingExceptionType exceptionType) {
    super(exceptionType.message());
    this.exceptionType = exceptionType;
  }

  @Override
  public BaseExceptionType exceptionType() {
    return exceptionType;
  }
}
