package briefing.briefing.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Briefing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BriefingType type;
  @Column(nullable = false)
  private Integer rank;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String subtitle;
  @Column(nullable = false, length = 1024)
  private String content;

  @OneToMany(mappedBy = "briefing", cascade = CascadeType.PERSIST)
  private List<Article> articles = new ArrayList<>();

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  public Briefing(
      final BriefingType type,
      final Integer rank,
      final String title,
      final String subtitle,
      final String content
  ) {
    this.type = type;
    this.rank = rank;
    this.title = title;
    this.subtitle = subtitle;
    this.content = content;
  }
}
