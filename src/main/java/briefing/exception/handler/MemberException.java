package briefing.exception.handler;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(ErrorCode errorCode){
        super(errorCode);
    }
}
