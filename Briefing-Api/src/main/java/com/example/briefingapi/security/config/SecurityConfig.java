package com.example.briefingapi.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import com.example.briefingapi.security.filter.JwtRequestFilter;
import com.example.briefingapi.security.handler.JwtAccessDeniedHandler;
import com.example.briefingapi.security.handler.JwtAuthenticationEntryPoint;
import com.example.briefingapi.security.handler.JwtAuthenticationExceptionHandler;
import com.example.briefingapi.security.handler.SwaggerLoginSuccessHandler;
import com.example.briefingapi.security.provider.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.NullRoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint =
            new JwtAuthenticationEntryPoint();

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler = new JwtAccessDeniedHandler();

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler =
            new JwtAuthenticationExceptionHandler();

    private static final String[] WHITE_LIST = {};

    private final SwaggerLoginSuccessHandler swaggerLoginSuccessHandler =
            new SwaggerLoginSuccessHandler();

    @Value("${swagger.login.id}")
    private String swaggerId;

    @Value("${swagger.login.password}")
    private String swaggerPass;

    private static final String[] JWT_WHITE_LIST ={
            "/pushs","/members/auth","/v2/members/auth",
            "briefings", "/v2/briefings","/chattings",
            "/briefings/temp"
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
        return (web) ->
                web.ignoring()
                        .requestMatchers(
                                "","/",
                                "/schedule",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/docs/**");
    }

    @Bean
    @Order(1)
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/swagger-ui/**", "/login")
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // 비활성화
                .sessionManagement(
                        manage -> manage.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(
                        authorize ->
                                authorize
                                        .successHandler(swaggerLoginSuccessHandler)
                                        .defaultSuccessUrl("/swagger-ui/index.html")
                                        .permitAll())
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers("/swagger-ui/index.html")
                                        .authenticated()
                                        .anyRequest()
                                        .permitAll())
                .build();
    }

    @Bean
    public SecurityFilterChain JwtFilterChain(HttpSecurity http) throws Exception {
        return http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // 비활성화
                .sessionManagement(
                        manage ->
                                manage.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS)) // Session 사용 안함
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> {
                            authorize
                                    .requestMatchers("/v2/briefings/**")
                                    .permitAll(); // 모두 접근 가능합니다.
                            authorize.requestMatchers("/briefings/**").permitAll(); // 모두 접근 가능합니다.
                            authorize.requestMatchers("/v2/members/auth/**").permitAll();
                            authorize.requestMatchers("/members/auth/**").permitAll();
                            authorize.requestMatchers("/chattings/**").permitAll();
                            authorize
                                    .requestMatchers(HttpMethod.DELETE, "/v2/members/{memberId}")
                                    .authenticated();
                            authorize
                                    .requestMatchers(HttpMethod.DELETE, "/members/{memberId}")
                                    .authenticated();
                            authorize.requestMatchers("/v2/scraps/**").authenticated();
                            authorize.requestMatchers("/scraps/**").authenticated();
                            authorize.requestMatchers("/members/auth/token").permitAll();
                            authorize.anyRequest().authenticated();
                        })
                .exceptionHandling(
                        exceptionHandling ->
                                exceptionHandling
                                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilterBefore(
                        new JwtRequestFilter(tokenProvider,JWT_WHITE_LIST),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationExceptionHandler, JwtRequestFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails =
                User.builder()
                        .username(swaggerId)
                        .password(passwordEncoder().encode(swaggerPass))
                        .roles("USER", "ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(userDetails);
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
