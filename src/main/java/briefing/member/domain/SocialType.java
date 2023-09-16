package briefing.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {

    GOOGLE("google", "구글"),
    APPLE("apple", "애플");

    private final String value;
    private final String description;
}
