package com.mmark09.typingspeedtest.auth;

import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // for now, this will do
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

    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User response = authService.login(request);
        return ResponseEntity.ok().body(response);
    }
}
