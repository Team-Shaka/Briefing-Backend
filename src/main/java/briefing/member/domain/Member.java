package briefing.member.domain;

import briefing.base.BaseDateTimeEntity;
import briefing.scrap.domain.Scrap;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImgUrl;

    private String nickName;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.INACTIVE;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.ROLE_USER;

    // cascade 설정
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();
}
