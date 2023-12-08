package briefing.security.config;

import briefing.security.filter.JwtRequestFilter;
import briefing.security.handler.JwtAccessDeniedHandler;
import briefing.security.handler.JwtAuthenticationEntryPoint;
import briefing.security.handler.JwtAuthenticationExceptionHandler;
import briefing.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.NullRoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler = new JwtAccessDeniedHandler();

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler = new JwtAuthenticationExceptionHandler();

    private static final String[] WHITE_LIST = {

    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return new NullRoleHierarchy();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "",
                "/",
                "/schedule",
                "/swagger-ui.html",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-ui/index.html",
                "/swagger-ui/**",
                "/docs/**","/briefings/temp","/v2/briefings");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // 비활성화
                .sessionManagement(manage -> manage.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Session 사용 안함
                .formLogin(AbstractHttpConfigurer::disable)     // form login 사용 안함
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/v2/briefings/**").permitAll();  // 모두 접근 가능합니다.
                    authorize.requestMatchers("/briefings/**").permitAll();  // 모두 접근 가능합니다.
                    authorize.requestMatchers("/members/auth/**").permitAll();
                    authorize.requestMatchers("/chattings/**").permitAll();
                    authorize.requestMatchers(HttpMethod.DELETE, "/members/{memberId}").authenticated();
                    authorize.requestMatchers("/scraps/**").authenticated();
                    authorize.anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilterBefore(new JwtRequestFilter(tokenProvider),  UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationExceptionHandler,JwtRequestFilter.class)
                .build();
    }

    public CorsConfigurationSource corsConfiguration() {
        return request -> {
            org.springframework.web.cors.CorsConfiguration config =
                    new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            return config;
        };
    }
}
