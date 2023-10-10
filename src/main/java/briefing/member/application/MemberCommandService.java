package briefing.member.application;

import briefing.exception.ErrorCode;
import briefing.exception.handler.AppleOAuthException;
import briefing.exception.handler.MemberException;
import briefing.feign.oauth.apple.client.AppleOauth2Client;
import briefing.feign.oauth.apple.dto.ApplePublicKey;
import briefing.feign.oauth.apple.dto.ApplePublicKeyList;
import briefing.feign.oauth.google.client.GoogleOauth2Client;
import briefing.feign.oauth.google.dto.GoogleUserInfo;
import briefing.member.api.MemberConverter;
import briefing.member.application.dto.MemberRequest;
import briefing.member.domain.Member;
import briefing.member.domain.MemberRole;
import briefing.member.domain.MemberStatus;
import briefing.member.domain.SocialType;
import briefing.member.domain.repository.MemberRepository;
import briefing.redis.domain.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final GoogleOauth2Client googleOauth2Client;

    private final AppleOauth2Client appleOauth2Client;

    Logger logger = LoggerFactory.getLogger(MemberCommandService.class);


    public Member login(SocialType socialType, MemberRequest.LoginDTO request) {
        return switch (socialType) {
            case GOOGLE -> loginWithGoogle(request);
            case APPLE -> loginWithApple(request);
        };
    }

    private Member loginWithGoogle(MemberRequest.LoginDTO request) {
        // 구글에서 사용자 정보 조회
        GoogleUserInfo googleUserInfo = googleOauth2Client.verifyToken(request.getIdentityToken());

        Member member = memberRepository.findBySocialIdAndSocialType(googleUserInfo.getSub(), SocialType.GOOGLE)
                .orElseGet(() -> MemberConverter.toMember(googleUserInfo));

        return memberRepository.save(member);
    }

    private Member loginWithApple(MemberRequest.LoginDTO request) {

        ApplePublicKeyList applePublicKeys = appleOauth2Client.getApplePublicKeys();
        ApplePublicKey applePublicKey = null;

        try {
            JSONParser parser = new JSONParser();
            String[] decodeArr = request.getIdentityToken().split("\\.");

            System.out.println("decode Arr의 값");
            System.out.println(decodeArr);
            String header = new String(Base64.getDecoder().decode(decodeArr[0]));

            JSONObject headerJson = (JSONObject) parser.parse(header);

            System.out.println("identity token의 전자서명 정보");
            System.out.println(headerJson);

            Object kid = headerJson.get("kid");
            Object alg = headerJson.get("alg");

            applePublicKey = applePublicKeys.getMatchesKey(String.valueOf(alg), String.valueOf(kid));
        }catch (ParseException e){
            e.printStackTrace();
        }

        System.out.println("매칭이 된 key 정보");
        System.out.println(applePublicKey.toString());
        PublicKey publicKey = this.getPublicKey(applePublicKey);

        Claims userInfo = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(request.getIdentityToken()).getBody();

        System.out.println("파싱된 유저의 정보");
        System.out.println(userInfo);

        String appleSocialId = userInfo.get("sub", String.class);

        Optional<Member> foundMember = memberRepository.findBySocialIdAndSocialType(appleSocialId, SocialType.APPLE);

        return foundMember.isEmpty() ? memberRepository.save(MemberConverter.toMember(appleSocialId)) : foundMember.get();
    }

    private PublicKey getPublicKey(ApplePublicKey applePublicKeyDTO) {

        logger.info("전자서명을 위한 공개키 재료 : {}", applePublicKeyDTO.toString());

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

    public Member parseRefreshToken(RefreshToken refreshToken){
        return memberRepository.findById(refreshToken.getMemberId()).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public void deleteMember(Long memberId){
        memberRepository.deleteById(memberId);
    }
}
