package briefing.member.implement;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import briefing.common.enums.SocialType;
import briefing.common.exception.AppleOAuthException;
import briefing.common.exception.common.ErrorCode;
import briefing.infra.feign.oauth.apple.client.AppleOauth2Client;
import briefing.infra.feign.oauth.apple.dto.ApplePublicKey;
import briefing.infra.feign.oauth.apple.dto.ApplePublicKeyList;
import briefing.member.business.MemberConverter;
import briefing.member.domain.Member;
import briefing.member.domain.repository.MemberRepository;
import briefing.member.presentation.dto.MemberRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final AppleOauth2Client appleOauth2Client;

    Logger logger = LoggerFactory.getLogger(MemberCommandService.class);

    public Member login(MemberRequest.LoginDTO request) {
        return loginWithApple(request);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    private Member loginWithApple(MemberRequest.LoginDTO request) {

        ApplePublicKeyList applePublicKeys = appleOauth2Client.getApplePublicKeys();
        ApplePublicKey applePublicKey = null;

        try {
            JSONParser parser = new JSONParser();
            String[] decodeArr = request.getIdentityToken().split("\\.");

            String header = new String(Base64.getDecoder().decode(decodeArr[0]));

            JSONObject headerJson = (JSONObject) parser.parse(header);

            Object kid = headerJson.get("kid");
            Object alg = headerJson.get("alg");

            applePublicKey =
                    applePublicKeys.getMatchesKey(String.valueOf(alg), String.valueOf(kid));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PublicKey publicKey = this.getPublicKey(applePublicKey);

        Claims userInfo =
                Jwts.parserBuilder()
                        .setSigningKey(publicKey)
                        .build()
                        .parseClaimsJws(request.getIdentityToken())
                        .getBody();

        String appleSocialId = userInfo.get("sub", String.class);

        Optional<Member> foundMember =
                memberRepository.findBySocialIdAndSocialType(appleSocialId, SocialType.APPLE);

        return foundMember.isEmpty()
                ? memberRepository.save(MemberConverter.toMember(appleSocialId))
                : foundMember.get();
    }

    private PublicKey getPublicKey(ApplePublicKey applePublicKeyDTO) {

        String nStr = applePublicKeyDTO.getN();
        String eStr = applePublicKeyDTO.getE();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr);
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr);

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKeyDTO.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (Exception exception) {
            throw new AppleOAuthException(ErrorCode.FAIL_TO_MAKE_APPLE_PUBLIC_KEY);
        }
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
