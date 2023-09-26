package briefing.member.application.dto;

import lombok.Getter;

public class MemberRequest {

    @Getter
    public static class LoginDTO {
        private String identityToken;
    }

    @Getter
    public static class ReissueDTO{
        private String refreshToken;
    }
}
