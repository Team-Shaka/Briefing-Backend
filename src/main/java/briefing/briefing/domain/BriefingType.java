package briefing.briefing.domain;

import java.util.Arrays;

import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BriefingType {
  KOREA("Korea"),
  GLOBAL("Global"),
  SOCIAL("Social"),
  SCIENCE("Science"),
  ECONOMY("Economy");

  private final String value;

  public static BriefingType from(final String type) {
    return Arrays.stream(values())
        .filter(value -> value.getValue().equals(type))
        .findAny()
        .orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_TYPE));
  }
}
