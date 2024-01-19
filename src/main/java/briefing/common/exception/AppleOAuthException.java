package briefing.common.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class AppleOAuthException extends GeneralException {
    public AppleOAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
