package briefing.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import briefing.member.domain.Member;
import briefing.member.domain.SocialType;

public interface MemberRepository extends JpaRepository<Member, Long> {
Optional<Member> findBySocialIdAndSocialType(String socialId, SocialType socialType);

Optional<Member> findFirstByOrderByCreatedAt();
}
