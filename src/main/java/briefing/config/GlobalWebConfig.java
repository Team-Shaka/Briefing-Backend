package briefing.config;

import briefing.converter.BriefingTypeRequestConverter;
import briefing.converter.GptModelRequestConverter;
import briefing.converter.MessageRoleRequestConverter;
import briefing.converter.SocialTypeRequestConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalWebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(final FormatterRegistry registry) {
    registry.addConverter(new BriefingTypeRequestConverter());
    registry.addConverter(new GptModelRequestConverter());
    registry.addConverter(new MessageRoleRequestConverter());
    registry.addConverter(new SocialTypeRequestConverter());
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000", "https://briefing-web.vercel.app","https://dev.newsbreifing.store");
  }
}
