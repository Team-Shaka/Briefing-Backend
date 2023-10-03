package briefing.chatting.domain;

import briefing.exception.ErrorCode;
import briefing.exception.handler.ChattingException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageRole {
  SYSTEM("system"),
  ASSISTANT("assistant"),
  USER("user");

  @JsonValue
  private final String value;

  @JsonCreator
  public static MessageRole from(final String role) {
    return Arrays.stream(values())
        .filter(value -> value.getValue().equals(role))
        .findAny()
        .orElseThrow(() -> new ChattingException(ErrorCode.NOT_FOUND_ROLE));
  }

  public boolean isNotUser() {
    return !this.equals(USER);
  }
}
