package com.mmark09.typingspeedtest.user;

import com.mmark09.typingspeedtest.exceptions.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with username %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,username)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findByUsername(user.getUsername())
                .isPresent();
        if(userExists){
            throw new UsernameAlreadyExistsException();
        }
        String encodedPw = bCryptPasswordEncoder
                .encode(user.getPassword());
        user.setPassword(encodedPw);
        userRepository.save(user);

        return "saved";
    }
}
