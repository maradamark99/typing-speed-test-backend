package com.maradamark09.typingspeedtest.config;

import com.maradamark09.typingspeedtest.auth.UserDetailsImpl;
import com.maradamark09.typingspeedtest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> UserDetailsImpl.build(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public Collection<PublicEndpoint> getPublicEndpoints() {
        return List.of(
                new PublicEndpoint("/api/v1/auth/**", Collections.emptySet()),
                new PublicEndpoint("/api/v1/words/**", Collections.singleton(HttpMethod.GET)),
                new PublicEndpoint("/api/v1/difficulties/**", Collections.singleton(HttpMethod.GET)),
                new PublicEndpoint("/swagger-ui/**", Collections.emptySet()),
                new PublicEndpoint("/v3/api-docs/**", Collections.emptySet())
        );
    }

}
