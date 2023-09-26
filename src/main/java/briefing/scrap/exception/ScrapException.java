package briefing.scrap.exception;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class ScrapException extends GeneralException {
    public ScrapException(ErrorCode errorCode){
        super(errorCode);
    }
}
