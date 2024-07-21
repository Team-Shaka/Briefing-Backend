package com.example.briefingapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.briefingapi.exception.JwtAuthenticationException;
import com.example.briefingcommon.common.exception.common.ApiErrorResult;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;


// @Component
public class JwtAuthenticationExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException authException) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            PrintWriter writer = response.getWriter();
            String errorCodeName = authException.getMessage();
            ErrorCode code = ErrorCode.valueOf(errorCodeName);

            ApiErrorResult apiErrorResult =
                    ApiErrorResult.builder()
                            .isSuccess(false)
                            .code(code.getCode())
                            .message(code.getMessage())
                            .result(null)
                            .build();

            writer.write(apiErrorResult.toString());
            writer.flush();
            writer.close();
        }
    }
}
