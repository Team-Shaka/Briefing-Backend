package com.example.briefingcommon.domain.repository.member;


import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<Member> findFirstByOrderByCreatedAt();
}
