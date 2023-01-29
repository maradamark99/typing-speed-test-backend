package com.maradamark09.typingspeedtest.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JWTUtil {

    // TODO: change this to an injected value
    private final static String SECRET = "+N$cB^N6Kx%K7Vzdnpu+Jg^qBnNBjA9b";
    public final static String TOKEN_PREFIX = "Bearer ";

    private final static long EXPIRATION_TIME = 900_000;

    private final static Algorithm algorithm = Algorithm.HMAC512(SECRET.getBytes());

    public static DecodedJWT decodeToken(String encodedToken) {
        return JWT.require(algorithm)
                .build()
                .verify(encodedToken.replace(TOKEN_PREFIX, ""));
    }

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

}
