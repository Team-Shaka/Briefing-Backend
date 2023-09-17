package briefing.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {

    GOOGLE("google", "구글"),
    APPLE("apple", "애플");

    private final String value;
    private final String description;

    public static SocialType findByValue(String value) {
        for (SocialType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SocialType value: " + value);
    }
    @JsonValue
    String getSocialType() {
        return this.name().toLowerCase();
    }
}
