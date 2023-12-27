package briefing.feign.oauth.apple.dto;

import java.util.List;

import briefing.exception.ErrorCode;
import briefing.exception.handler.AppleOAuthException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplePublicKeyList {
    private List<ApplePublicKey> keys;

    public ApplePublicKey getMatchesKey(String alg, String kid) {
        return this.keys.stream()
                .filter(k -> k.getAlg().equals(alg) && k.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new AppleOAuthException(ErrorCode.APPLE_BAD_REQUEST));
    }
}
