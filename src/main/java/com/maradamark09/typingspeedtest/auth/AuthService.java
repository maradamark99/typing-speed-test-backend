package com.maradamark09.typingspeedtest.auth;

import com.maradamark09.typingspeedtest.util.JWTUtil;
import com.maradamark09.typingspeedtest.role.RoleRepository;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public void register(RegistrationRequest registrationRequest) {

        if(userRepository.findByEmail(registrationRequest.email()).isPresent()
                || userRepository.findByUsername(registrationRequest.username()).isPresent())
            throw new UserAlreadyExistsException();

        var role = roleRepository.findByValueIgnoreCase("USER");
        var userToSave =
                User.builder()
                        .email(registrationRequest.email())
                        .username(registrationRequest.username())
                        .password(passwordEncoder.encode(registrationRequest.password()))
                        .role(role)
                        .build();

        userRepository.save(userToSave);
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        var token = JWTUtil.generateToken(loginRequest.username());
        return "{\"token\": \"" + token + "\"}";
    }
}
