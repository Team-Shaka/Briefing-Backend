package briefing.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIVersion {
    V1_0_0("1.0.0"),
    V1_1_0("1.1.0"),
    V2_0_0("2.0.0");

    private final String version;

    @JsonValue
    String getAPIVersion() {
        return this.getVersion();
    }
}
