package com.maradamark09.typingspeedtest.jwt;

import com.maradamark09.typingspeedtest.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.maradamark09.typingspeedtest.jwt.JWTUtils.TOKEN_PREFIX;
import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class JWTAuthFilter extends OncePerRequestFilter {



    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get the authorization header from the request
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // if the request doesn't contain an auth header or the value doesn't start with Bearer
        // then invoke the next filter in the filter chain
        if(!hasText(authHeader) || (hasText(authHeader) && !authHeader.startsWith(TOKEN_PREFIX))) {
            filterChain.doFilter(request, response);
            return;
        }

        final var token = authHeader.split(" ")[1].trim();
        var authentication = getAuthentication(token);
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    // decode and validate the token, then return an authentication object
    private UsernamePasswordAuthenticationToken getAuthentication(String encodedToken) {

        var decodedToken = JWTUtils.decodeToken(encodedToken);
        var user = decodedToken.getSubject();

        var userDetails = userRepository.findByUsername(user).orElse(null);

        return new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ? Collections.emptySet() : Collections.singleton(
                        new SimpleGrantedAuthority(
                                userDetails.getRole()
                                        .toString()
                        )
                )
        );
    }

}