package briefing.converter;

import briefing.chatting.domain.MessageRole;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class MessageRoleRequestConverter implements Converter<String, MessageRole> {

  @Override
  public MessageRole convert(@NonNull final String role) {
    return MessageRole.from(role);
  }
}
