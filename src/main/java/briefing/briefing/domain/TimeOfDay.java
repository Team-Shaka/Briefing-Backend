package briefing.briefing.domain;

import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TimeOfDay {
    MORNING("Morning"),
    EVENING("Evening");

    private final String description;

    public static TimeOfDay findByValue(String type) {
        return Arrays.stream(values())
                .filter(value -> value.getDescription().equals(type))
                .findAny()
                .orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_TYPE));
    }

    @JsonValue
    String getTimeOfDay() {
        return this.getDescription();
    }
}
