package briefing.briefing.domain;

import briefing.base.BaseDateTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  @OneToMany(mappedBy = "briefing", fetch = FetchType.LAZY)
  private List<BriefingArticle> briefingArticles = new ArrayList<>();

//  public Briefing(final BriefingType type, final Integer ranks, final String title,
//      final String subtitle, final String content) {
//    this.type = type;
//    this.ranks = ranks;
//    this.title = title;
//    this.subtitle = subtitle;
//    this.content = content;
//  }
}
