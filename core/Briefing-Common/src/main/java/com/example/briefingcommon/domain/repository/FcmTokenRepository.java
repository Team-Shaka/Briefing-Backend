package com.example.briefingcommon.domain.repository;

import com.example.briefingcommon.entity.FcmToken;
import com.example.briefingcommon.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    Optional<FcmToken> findByMemberAndToken(Member member, String token);
}
