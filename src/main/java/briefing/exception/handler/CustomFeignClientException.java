package briefing.exception.handler;

import briefing.exception.ErrorCode;
import briefing.exception.GeneralException;

public class CustomFeignClientException extends GeneralException {

    public CustomFeignClientException(ErrorCode errorCode){
        super(errorCode);
    }
}
