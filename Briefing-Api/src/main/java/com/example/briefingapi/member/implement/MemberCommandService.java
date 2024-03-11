package com.example.briefingapi.member.implement;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.example.briefingapi.member.business.MemberConverter;
import com.example.briefingcommon.domain.repository.FcmTokenRepository;
import com.example.briefingcommon.domain.repository.member.MemberRepository;
import com.example.briefingapi.member.presentation.dto.MemberRequest;
import com.example.briefingcommon.common.exception.AppleOAuthException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.FcmToken;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.SocialType;
import com.example.briefinginfra.feign.nickname.hwanmoo.adapter.NickNameGenerator;
import com.example.briefinginfra.feign.oauth.apple.client.AppleOauth2Client;
import com.example.briefinginfra.feign.oauth.apple.dto.ApplePublicKey;
import com.example.briefinginfra.feign.oauth.apple.dto.ApplePublicKeyList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final AppleOauth2Client appleOauth2Client;
    private final NickNameGenerator nickNameGenerator;

    private final FcmTokenRepository fcmTokenRepository;

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

        String nickName = nickNameGenerator.getOneRandomNickName();

        return foundMember.isEmpty()
                ? memberRepository.save(MemberConverter.toMember(appleSocialId, nickName))
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


    public void storeFcmToken(String token, Member member){

        Optional<FcmToken> optionalFcmToken = fcmTokenRepository.findByMemberAndToken(member, token);

        optionalFcmToken.ifPresentOrElse(
                fcmToken -> {},
                () -> fcmTokenRepository.save(FcmToken.builder()
                                .token(token)
                                .member(member)
                        .build())
        );
    }

    public void abortFcmToken(String token, Member member){

        Optional<FcmToken> optionalFcmToken = fcmTokenRepository.findByMemberAndToken(member, token);

        optionalFcmToken.ifPresentOrElse(
                fcmTokenRepository::delete,
                () -> {}
        );
    }
}
