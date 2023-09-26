package briefing.exception.handler;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class RefreshTokenException extends GeneralException {
    public RefreshTokenException(ErrorCode errorCode){
        super(errorCode);
    }
}
