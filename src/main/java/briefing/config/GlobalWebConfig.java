package briefing.config;

import briefing.converter.*;
import briefing.security.handler.annotation.AuthUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GlobalWebConfig implements WebMvcConfigurer {

  private final AuthUserArgumentResolver authUserArgumentResolver;
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
    resolverList.add(authUserArgumentResolver);
  }
  @Override
  public void addFormatters(final FormatterRegistry registry) {
    registry.addConverter(new BriefingTypeRequestConverter());
    registry.addConverter(new GptModelRequestConverter());
    registry.addConverter(new MessageRoleRequestConverter());
    registry.addConverter(new SocialTypeRequestConverter());
    registry.addConverter(new TimeOfDayConverter());
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000", "https://briefing-web.vercel.app","https://dev.newsbreifing.store");
  }
}
