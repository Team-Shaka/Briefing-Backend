package com.example.briefingapi.security.provider;

import com.example.briefingapi.exception.JwtAuthenticationException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    private final String AUTHORITIES_KEY;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final String secret;

    private final long accessTokenValidityInMilliseconds;

    private Key key;

    public enum TokenType {
        ACCESS,
        REFRESH;
    }

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.authorities-key}") String authoritiesKey,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds) {
        this.secret = secret;
        this.AUTHORITIES_KEY = authoritiesKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long userId, String socialType, String socialId, Collection<? extends GrantedAuthority> authorities) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(accessTokenValidityInMilliseconds, ChronoUnit.MILLIS);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .claim("socialType", socialType)
                .claim("socialID", socialId)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token, TokenType type) throws JwtAuthenticationException {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            LOGGER.info("JWT Token is valid. Expiration: {}", claimsJws.getBody().getExpiration());
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        } catch (ExpiredJwtException e) {
            LOGGER.warn("Expired JWT token: {}", e.getMessage());
            throw new JwtAuthenticationException(ErrorCode.EXPIRED_JWT_EXCEPTION);
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token: {}", e.getMessage());
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT token compact of handler are invalid: {}", e.getMessage());
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN_EXCEPTION);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
