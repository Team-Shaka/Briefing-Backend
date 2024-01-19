package briefing.common.presentation.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.common.enums.SocialType;

public class SocialTypeRequestConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String source) {
        return SocialType.findByValue(source);
    }
}
