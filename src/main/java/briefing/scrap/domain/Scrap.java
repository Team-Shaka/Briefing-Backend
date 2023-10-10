package briefing.scrap.domain;

import briefing.base.BaseDateTimeEntity;
import briefing.briefing.domain.Briefing;
import briefing.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void setMember(Member member){
        if (this.member != null)
            this.member.getScrapList().remove(this);
        this.member = member;
        member.getScrapList().add(this);
    }
}
