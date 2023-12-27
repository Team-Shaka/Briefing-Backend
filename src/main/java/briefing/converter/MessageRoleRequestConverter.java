package briefing.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.chatting.domain.MessageRole;
import lombok.NonNull;

public class MessageRoleRequestConverter implements Converter<String, MessageRole> {

    @Override
    public MessageRole convert(@NonNull final String role) {
        return MessageRole.from(role);
    }
}
