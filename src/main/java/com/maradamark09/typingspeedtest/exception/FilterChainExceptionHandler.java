package com.maradamark09.typingspeedtest.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        }
        catch (JWTVerificationException e) {
            var status = HttpStatus.UNAUTHORIZED;
            var body = setResponse(
                    status,
                    e.getMessage()
            );
            sendResponse(response, status, body);
        }
        catch (Exception e) {
            var status = HttpStatus.INTERNAL_SERVER_ERROR;
            var body = setResponse(
                    status,
                    e.getMessage()
            );
            sendResponse(response, status, body);
        }

    }

    private String setResponse(HttpStatus status, String message) throws JsonProcessingException {
        var errorResponse = MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(message)
                .build();
        return objectMapper.writeValueAsString(errorResponse);
    }

    private void sendResponse(HttpServletResponse httpServletResponse, HttpStatus status, String json) throws IOException {
        httpServletResponse.setStatus(status.value());
        httpServletResponse.getWriter().write(json);
        httpServletResponse.getWriter().flush();
    }


}
