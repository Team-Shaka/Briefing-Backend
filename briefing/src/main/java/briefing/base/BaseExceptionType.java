package briefing.base;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

  HttpStatus status();

  String message();
}
