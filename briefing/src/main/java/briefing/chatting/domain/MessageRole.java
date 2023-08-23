package briefing.chatting.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageRole {
  SYSTEM("System"),
  BOT("Bot"),
  USER("User");

  private final String value;
}
