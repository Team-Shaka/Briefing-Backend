package briefing.common.enums;

import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum APIVersion {
    V1_0_0("1.0.0"),
    V1_1_0("1.1.0"),
    V2_0_0("2.0.0");

    private final String version;

    public static APIVersion findByValue(String type) {
        return Arrays.stream(values())
                .filter(value -> value.getVersion().equals(type))
                .findAny()
                .orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_TYPE));
    }

    @JsonValue
    String getAPIVersion() {
        return this.getVersion();
    }
}
