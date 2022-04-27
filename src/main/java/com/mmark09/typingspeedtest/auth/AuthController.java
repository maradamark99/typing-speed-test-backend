package com.mmark09.typingspeedtest.auth;

import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private AuthService authService;

    @GetMapping
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @PostMapping(path = "/register")
    @ResponseBody
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request){
        User response = authService.register(request);
        return ResponseEntity.ok().body(response);
    }

}
