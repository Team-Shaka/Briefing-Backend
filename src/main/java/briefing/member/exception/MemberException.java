package briefing.member.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
