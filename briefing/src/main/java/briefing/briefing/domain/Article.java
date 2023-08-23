package briefing.briefing.domain;

import briefing.BaseDateTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String press;
  @Column(nullable = false)
  private String title;
  @Column(length = 1024)
  private String url;

  public Article(final String press, final String title, final String url) {
    this.press = press;
    this.title = title;
    this.url = url;
  }
}
