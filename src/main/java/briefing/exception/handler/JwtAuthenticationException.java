package briefing.exception.handler;

import org.springframework.security.core.AuthenticationException;

import briefing.exception.ErrorCode;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(ErrorCode code) {
        super(code.name());
    }
}
