package briefing.briefing.domain;

import briefing.base.BaseDateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BriefingArticle extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Briefing briefing;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Article article;

  public BriefingArticle(final Briefing briefing, final Article article) {
    this.briefing = briefing;
    this.article = article;
  }
}
