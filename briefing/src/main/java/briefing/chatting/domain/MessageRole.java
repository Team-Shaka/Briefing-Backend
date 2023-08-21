package briefing.chatting.domain;

import lombok.Getter;

@Getter
public enum MessageRole {
  SYSTEM("System"),
  BOT("Bot"),
  USER("User");

  private final String value;

  MessageRole(final String value) {
    this.value = value;
  }
}
