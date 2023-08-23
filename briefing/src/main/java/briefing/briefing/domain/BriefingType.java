package briefing.briefing.domain;

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
        .orElseThrow(() -> new IllegalArgumentException("타입이 존재하지 않습니다."));
  }
}
