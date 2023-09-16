package briefing.converter;

import briefing.member.domain.SocialType;
import org.springframework.core.convert.converter.Converter;

public class SocialTypeRequestConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String source) {
        return SocialType.findByValue(source);
    }

}
