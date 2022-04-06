package com.mmark09.typingspeedtest.registration;

import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private RegistrationService registrationService;

    @GetMapping
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
}
