package briefing.common.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class RefreshTokenException extends GeneralException {
    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
