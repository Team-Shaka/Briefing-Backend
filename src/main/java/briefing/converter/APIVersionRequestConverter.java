package briefing.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.common.enums.APIVersion;
import lombok.NonNull;

public class APIVersionRequestConverter implements Converter<String, APIVersion> {

    @Override
    public APIVersion convert(@NonNull final String source) {
        return APIVersion.findByValue(source);
    }
}
