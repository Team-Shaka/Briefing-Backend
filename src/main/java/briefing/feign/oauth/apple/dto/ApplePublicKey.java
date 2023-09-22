package briefing.feign.oauth.apple.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplePublicKey {

    @Override
    public String toString() {
        return "ApplePublicKeyDTO{" +
                "kty='" + kty + '\'' +
                ", kid='" + kid + '\'' +
                ", use='" + use + '\'' +
                ", alg='" + alg + '\'' +
                ", n='" + n + '\'' +
                ", e='" + e + '\'' +
                '}';
    }

    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
}
