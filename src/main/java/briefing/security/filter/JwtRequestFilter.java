package briefing.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import briefing.security.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
private final TokenProvider tokenProvider;

@Override
protected void doFilterInternal(
	HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	throws ServletException, IOException {
	HttpServletRequest httpServletRequest = request;
	String jwt = tokenProvider.resolveToken(httpServletRequest);

	if (StringUtils.hasText(jwt)
		&& tokenProvider.validateToken(jwt, TokenProvider.TokenType.ACCESS)) {

	Authentication authentication = tokenProvider.getAuthentication(jwt);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	} else {
	SecurityContextHolder.getContext().setAuthentication(null);
	}
	filterChain.doFilter(httpServletRequest, response);
}
}
