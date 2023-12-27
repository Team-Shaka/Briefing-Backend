package briefing.scrap.domain;

import jakarta.persistence.*;

import briefing.base.BaseDateTimeEntity;
import briefing.briefing.domain.Briefing;
import briefing.member.domain.Member;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "briefing_id"})})
public class Scrap extends BaseDateTimeEntity {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(nullable = false)
private Member member;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(nullable = false)
private Briefing briefing;

public void setMember(Member member) {
	if (this.member != null) this.member.getScrapList().remove(this);
	this.member = member;
	member.getScrapList().add(this);
}
}
