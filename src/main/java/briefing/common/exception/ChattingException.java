package briefing.common.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class ChattingException extends GeneralException {
    public ChattingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
