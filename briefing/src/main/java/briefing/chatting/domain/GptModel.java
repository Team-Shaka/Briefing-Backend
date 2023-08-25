package briefing.chatting.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GptModel {
  GPT_3_5_TURBO("gpt-3.5-turbo");

  private final String value;

  @JsonCreator
  public static GptModel from(final String model) {
    return Arrays.stream(values())
        .filter(value -> value.getValue().equals(model))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("모델이 존재하지 않습니다."));
  }
}
