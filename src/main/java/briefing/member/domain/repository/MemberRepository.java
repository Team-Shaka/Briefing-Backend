package briefing.member.domain.repository;

import briefing.member.domain.Member;
import briefing.member.domain.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<Member> findFirstByOrderByCreatedAt();
}
