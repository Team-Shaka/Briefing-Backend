package briefing.security.config;

import briefing.security.filter.JwtRequestFilter;
import briefing.security.handler.JwtAuthenticationExceptionHandler;
import briefing.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http)throws Exception{
        JwtRequestFilter jwtFilter = new JwtRequestFilter(tokenProvider);
        JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler = new JwtAuthenticationExceptionHandler();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationExceptionHandler, JwtRequestFilter.class);
    }
}
