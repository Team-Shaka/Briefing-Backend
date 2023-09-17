package briefing.feign.oauth.google.dto;

import lombok.Data;

@Data
public class GoogleUserInfo {
    private String iss;
    private String azp;
    private String aud;
    private String sub; // User ID
    private String email;
    private boolean email_verified;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    // 필요한 다른 필드도 추가 가능
}