package briefing.member.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class MemberRequest {

    @Getter
    public static class LoginDTO {
        private String identityToken;
    }

    @Getter
    public static class ReissueDTO{
        @NotBlank
        private String refreshToken;
    }
}
