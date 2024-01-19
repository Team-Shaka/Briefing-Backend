package briefing.briefing.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class BriefingException extends GeneralException {
    public BriefingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
