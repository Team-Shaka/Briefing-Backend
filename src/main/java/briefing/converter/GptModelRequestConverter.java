package briefing.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.chatting.domain.GptModel;
import lombok.NonNull;

public class GptModelRequestConverter implements Converter<String, GptModel> {

@Override
public GptModel convert(@NonNull final String model) {
	return GptModel.from(model);
}
}
