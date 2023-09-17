package briefing.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{
    private final ErrorCode errorCode;

    public GeneralException() {
        super(ErrorCode._INTERNAL_SERVER_ERROR.getMessage());
        this.errorCode = ErrorCode._INTERNAL_SERVER_ERROR;
    }

    public GeneralException(String message) {
        super(ErrorCode._INTERNAL_SERVER_ERROR.getMessage(message));
        this.errorCode = ErrorCode._INTERNAL_SERVER_ERROR;
    }

    public GeneralException(String message, Throwable cause) {
        super(ErrorCode._INTERNAL_SERVER_ERROR.getMessage(message), cause);
        this.errorCode = ErrorCode._INTERNAL_SERVER_ERROR;
    }

    public GeneralException(Throwable cause) {
        super(ErrorCode._INTERNAL_SERVER_ERROR.getMessage(cause));
        this.errorCode = ErrorCode._INTERNAL_SERVER_ERROR;
    }

    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}
