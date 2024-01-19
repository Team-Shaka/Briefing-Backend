package briefing.scrap.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class ScrapException extends GeneralException {
    public ScrapException(ErrorCode errorCode) {
        super(errorCode);
    }
}
