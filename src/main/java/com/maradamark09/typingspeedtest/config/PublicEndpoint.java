package com.maradamark09.typingspeedtest.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Set;

@AllArgsConstructor
@Getter
public class PublicEndpoint {

    private String antPattern;

    /**
     * Contains all the HTTP method names that are allowed without auth for the particular endpoint
     * if it is empty, then everything is allowed.
     */
    private Set<HttpMethod> methodRestrictions;

}
