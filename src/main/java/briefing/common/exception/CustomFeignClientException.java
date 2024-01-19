package briefing.common.exception;

import briefing.common.exception.common.ErrorCode;
import briefing.common.exception.common.GeneralException;

public class CustomFeignClientException extends GeneralException {

    public CustomFeignClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
