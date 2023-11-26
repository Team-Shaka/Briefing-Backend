package briefing.briefing.domain;

import briefing.base.BaseDateTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Briefing extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BriefingType type;

  @Column(nullable = false)
  private Integer ranks;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String subtitle;

  @Column(nullable = false, length = 1000)
  private String content;

  @Builder.Default
  @OneToMany(mappedBy = "briefing", fetch = FetchType.LAZY)
  private List<BriefingArticle> briefingArticles = new ArrayList<>();

  @Builder.Default
  @Transient
  private Integer scrapCount = 0;

  public void setScrapCount(Integer scrapCount) {
    this.scrapCount = scrapCount;
  }
}
