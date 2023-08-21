package briefing.briefing.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum BriefingType {
  KOREA("Korea"),
  GLOBAL("Global");

  private final String value;

  BriefingType(final String value) {
    this.value = value;
  }
}
