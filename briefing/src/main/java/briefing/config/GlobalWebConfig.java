package briefing.config;

import briefing.converter.BriefingTypeRequestConverter;
import briefing.converter.GptModelRequestConverter;
import briefing.converter.MessageRoleRequestConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalWebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(final FormatterRegistry registry) {
    registry.addConverter(new BriefingTypeRequestConverter());
    registry.addConverter(new GptModelRequestConverter());
    registry.addConverter(new MessageRoleRequestConverter());
  }
}
