package com.mmark09.typingspeedtest.registration;

import com.mmark09.typingspeedtest.user.User;
import com.mmark09.typingspeedtest.user.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.json.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // for now, this will do
@RequestMapping(path = "api/v1/register")
@AllArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private RegistrationService registrationService;

    @GetMapping
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request){
        User response = registrationService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
