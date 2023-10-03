package briefing.exception.handler;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class ChattingException extends GeneralException {
    public ChattingException(ErrorCode errorCode){
        super(errorCode);
    }
}
