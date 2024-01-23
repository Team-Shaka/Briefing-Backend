package com.example.briefingapi.member.implement;

import java.util.Optional;

import com.example.briefingcommon.domain.repository.member.MemberRepository;
import com.example.briefingcommon.common.exception.MemberException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.MemberRole;
import com.example.briefingcommon.entity.enums.SocialType;
import com.example.briefingcommon.entity.redis.RefreshToken;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Optional<Member> findBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return memberRepository.findBySocialIdAndSocialType(socialId, socialType);
    }

    public Member testForTokenApi() {
        return memberRepository
                .findFirstByOrderByCreatedAt()
                .orElseGet(
                        () ->
                                memberRepository.save(
                                        Member.builder()
                                                .nickName(",,,!,1")
                                                .socialId("1234567")
                                                .socialType(SocialType.GOOGLE)
                                                .role(MemberRole.ROLE_USER)
                                                .build()));
    }

    public Member parseRefreshToken(RefreshToken refreshToken) {
        return memberRepository
                .findById(refreshToken.getMemberId())
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
