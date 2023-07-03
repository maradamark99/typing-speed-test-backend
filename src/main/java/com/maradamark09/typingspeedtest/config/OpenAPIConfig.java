package com.maradamark09.typingspeedtest.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(name = OpenAPIConfig.SECURITY_SCHEME_NAME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenAPIConfig {
    public static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
}