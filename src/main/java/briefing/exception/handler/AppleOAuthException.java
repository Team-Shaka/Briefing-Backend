package briefing.exception.handler;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class AppleOAuthException extends GeneralException {
    public AppleOAuthException(ErrorCode errorCode){
        super(errorCode);
    }
}
