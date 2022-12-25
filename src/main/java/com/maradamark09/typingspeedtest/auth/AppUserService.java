package com.maradamark09.typingspeedtest.auth;

import com.maradamark09.typingspeedtest.role.RoleRepository;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.user.UserRepository;
import com.maradamark09.typingspeedtest.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    public void saveUser(UserRequest userRequest) {
        // TODO: validation
        var role = roleRepository.findByValueIgnoreCase("USER");
        var userToSave =
                User.builder()
                        .id(UUID.randomUUID())
                        .email(userRequest.email())
                        .username(userRequest.username())
                        .password(bCryptPasswordEncoder.encode(userRequest.password()))
                        .role(role)
                .build();

        userRepository.save(userToSave);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        var role = new SimpleGrantedAuthority(
                user.getRole()
                        .toString()
        );

        return new AppUser(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(role)
        );
    }

}
