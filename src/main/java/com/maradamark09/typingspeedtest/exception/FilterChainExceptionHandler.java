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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        }
        catch (JWTVerificationException e) {
            var status = HttpServletResponse.SC_UNAUTHORIZED;
            var body = setResponse(
                    status,
                    e.getMessage()
            );
            sendResponse(response, status, body);
        }
        catch (Exception e) {
            var status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            var body = setResponse(
                    status,
                    e.getMessage()
            );
            sendResponse(response, status, body);
        }

    }

    private String setResponse(Integer statusCode, String message) throws JsonProcessingException {
        var errorResponse = new MyErrorResponse(HttpStatus.valueOf(statusCode), message);
        return objectMapper.writeValueAsString(errorResponse);
    }

    private void sendResponse(HttpServletResponse httpServletResponse, Integer status, String json) throws IOException {
        httpServletResponse.setStatus(status);
        httpServletResponse.getWriter().write(json);
        httpServletResponse.getWriter().flush();
    }


}
