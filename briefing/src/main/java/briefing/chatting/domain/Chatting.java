package briefing.chatting.domain;

import briefing.base.BaseDateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Chatting extends BaseDateTimeEntity {

  private static final int LIMIT_TITLE_LENGTH = 20;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  @OneToMany(mappedBy = "chatting", fetch = FetchType.LAZY)
  private List<Message> messages;

  public Chatting() {
  }

  public boolean isNotInitialized() {
    return title == null || title.isBlank();
  }

  public void updateTitle(final String content) {
    this.title = convertToTitle(content);
  }

  private String convertToTitle(final String content) {
    if (content.length() > LIMIT_TITLE_LENGTH) {
      final String substring = content.substring(0, LIMIT_TITLE_LENGTH - 3);
      return substring + "...";
    }
    return content;
  }
}
