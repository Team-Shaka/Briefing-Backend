package briefing.common.exception;

import org.springframework.security.core.AuthenticationException;

import briefing.common.exception.common.ErrorCode;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(ErrorCode code) {
        super(code.name());
    }
}
