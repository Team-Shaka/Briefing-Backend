package briefing.exception.handler;

import briefing.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(ErrorCode code){
        super(code.name());
    }
}
