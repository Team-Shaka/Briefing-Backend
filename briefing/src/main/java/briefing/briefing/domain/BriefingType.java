package briefing.briefing.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BriefingType {
  KOREA("Korea"),
  GLOBAL("Global");

  private final String value;
}
