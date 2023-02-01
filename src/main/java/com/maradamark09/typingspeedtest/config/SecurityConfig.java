package com.maradamark09.typingspeedtest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark09.typingspeedtest.exception.FilterChainExceptionHandler;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
import com.maradamark09.typingspeedtest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    private final Collection<PublicEndpoint> publicEndpoints;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((auth) ->
                        auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/difficulties/**", "/api/v1/words/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/results/**").hasAuthority("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/difficulties/**", "/api/v1/words/**", "/api/v1/results/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/difficulties/**", "/api/v1/words/**", "/api/v1/results/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/difficulties/**", "/api/v1/words/**", "/api/v1/results/**").permitAll()
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JWTAuthFilter(userRepository, publicEndpoints), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new FilterChainExceptionHandler(objectMapper), JWTAuthFilter.class)
                .build();

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
