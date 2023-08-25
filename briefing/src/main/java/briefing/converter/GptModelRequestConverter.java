package briefing.converter;

import briefing.chatting.domain.GptModel;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class GptModelRequestConverter implements Converter<String, GptModel> {

  @Override
  public GptModel convert(@NonNull final String model) {
    return GptModel.from(model);
  }
}
