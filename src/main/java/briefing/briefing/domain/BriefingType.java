package briefing.briefing.domain;

import briefing.briefing.exception.BriefingException;
import briefing.briefing.exception.BriefingExceptionType;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BriefingType {
  KOREA("Korea"),
  GLOBAL("Global");

  private final String value;

  public static BriefingType from(final String type) {
    return Arrays.stream(values())
        .filter(value -> value.getValue().equals(type))
        .findAny()
        .orElseThrow(() -> new BriefingException(BriefingExceptionType.NOT_FOUND_TYPE));
  }
}
