package briefing.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.briefing.domain.BriefingType;
import lombok.NonNull;

public class BriefingTypeRequestConverter implements Converter<String, BriefingType> {

    @Override
    public BriefingType convert(@NonNull final String type) {
        return BriefingType.from(type);
    }
}
