package briefing.briefing.domain;

import briefing.base.BaseDateTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String press;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false, length = 1024)
  private String url;

//  public Article(final String press, final String title, final String url) {
//    this.press = press;
//    this.title = title;
//    this.url = url;
//  }
}
