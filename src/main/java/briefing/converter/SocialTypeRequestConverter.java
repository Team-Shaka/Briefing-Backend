package briefing.converter;

import org.springframework.core.convert.converter.Converter;

import briefing.member.domain.SocialType;

public class SocialTypeRequestConverter implements Converter<String, SocialType> {
@Override
public SocialType convert(String source) {
	return SocialType.findByValue(source);
}
}
