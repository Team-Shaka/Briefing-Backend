package briefing.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponse {

    @Builder @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private Long memberId;
        private String accessToken;
        private String refreshToken;
    }
}
