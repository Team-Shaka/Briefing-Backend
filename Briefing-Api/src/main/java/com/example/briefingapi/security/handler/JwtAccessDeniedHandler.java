package com.example.briefingapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.briefingcommon.common.exception.common.ApiErrorResult;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(403);
        PrintWriter writer = response.getWriter();

        ApiErrorResult apiErrorResult =
                ApiErrorResult.builder()
                        .isSuccess(false)
                        .code(ErrorCode._FORBIDDEN.getCode())
                        .message(ErrorCode._FORBIDDEN.getMessage())
                        .result(null)
                        .build();
        try {
            writer.write(apiErrorResult.toString());
        } catch (NullPointerException e) {
            LOGGER.error("응답 메시지 작성 에러", e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
