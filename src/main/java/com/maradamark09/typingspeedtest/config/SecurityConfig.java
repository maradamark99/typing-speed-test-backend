package com.maradamark09.typingspeedtest.config;

import com.maradamark09.typingspeedtest.exception.FilterChainExceptionHandler;
import com.maradamark09.typingspeedtest.jwt.JWTAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/v1/difficulties/**", "/api/v1/words/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/difficulties/**", "/api/v1/words/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/difficulties/**", "/api/v1/words/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/difficulties/**", "/api/v1/words/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, JWTAuthFilter.class)
                .build();

    }

}
