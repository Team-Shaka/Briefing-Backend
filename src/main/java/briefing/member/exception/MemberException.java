package briefing.member.exception;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(ErrorCode errorCode){
        super(errorCode);
    }
}
