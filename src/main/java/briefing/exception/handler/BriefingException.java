package briefing.exception.handler;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class BriefingException extends GeneralException {
    public BriefingException(ErrorCode errorCode){
        super(errorCode);
    }
}
