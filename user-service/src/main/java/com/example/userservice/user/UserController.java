package com.example.userservice.user;

import com.example.userservice.account.AccountRepository;
import com.example.userservice.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println(user.getAccount().getId());
        if (accountRepository.findUserByUsername(user.getAccount().getUsername()) != null) {
            return ResponseEntity.ok(new AuthenticationResponse("Username is already used!"));
        } else {
            try {
                userRepository.save(user);
                return ResponseEntity.ok(new AuthenticationResponse("Registered", user.getAccount().getUsername()));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(new AuthenticationResponse("Mail is already use!"));
            }
        }
    }

    
}
