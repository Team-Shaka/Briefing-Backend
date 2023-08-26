package briefing;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

  HttpStatus status();

  String message();
}
