package com.maradamark09.typingspeedtest.config;

import com.maradamark09.typingspeedtest.exception.FilterChainExceptionHandler;
import com.maradamark09.typingspeedtest.user.UserRepository;
import com.maradamark09.typingspeedtest.auth.JWTAuthFilter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final FilterChainExceptionHandler exceptionHandler;

	private final JWTAuthFilter authFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers(HttpMethod.POST, "/api/v1/difficulties/**", "/api/v1/words/**")
						.hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/results/**").hasAuthority("USER")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/difficulties/**", "/api/v1/words/**",
								"/api/v1/results/**")
						.hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/difficulties/**", "/api/v1/words/**",
								"/api/v1/results/**")
						.hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/v1/difficulties/**", "/api/v1/words/**",
								"/api/v1/results/**")
						.permitAll()
						.requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
						.anyRequest().permitAll())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(exceptionHandler, JWTAuthFilter.class)
				.build();

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
