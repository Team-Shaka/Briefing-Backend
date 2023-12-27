package briefing.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    ROLE_GUEST("게스트"),
    ROLE_USER("회원"),
    ROLE_ADMIN("관리자");

    private final String description;
}
