package com.example.briefinginfra.feign.oauth.apple.dto;

import java.util.List;

import com.example.briefingcommon.common.exception.AppleOAuthException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
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
