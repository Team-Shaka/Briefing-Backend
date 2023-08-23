package briefing.converter;

import briefing.briefing.domain.BriefingType;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

public class BriefingTypeRequestConverter implements Converter<String, BriefingType> {

  @Override
  public BriefingType convert(@NonNull final String type) {
    return BriefingType.from(type);
  }
}
