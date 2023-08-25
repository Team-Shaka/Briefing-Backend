package briefing.chatting.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageRole {
  SYSTEM("System"),
  BOT("Bot"),
  USER("User");

  private final String value;

  @JsonCreator
  public static MessageRole from(final String role) {
    return Arrays.stream(values())
        .filter(value -> value.getValue().equals(role))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("역할이 존재하지 않습니다."));
  }

  public boolean isNotUser() {
    return !this.equals(USER);
  }
}
