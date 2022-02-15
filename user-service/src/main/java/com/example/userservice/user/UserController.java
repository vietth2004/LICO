package com.example.userservice.user;

import com.example.userservice.account.Account;
import com.example.userservice.account.AccountRepository;
import com.example.userservice.security.model.AuthenticationRequest;
import com.example.userservice.security.model.AuthenticationResponse;
import com.example.userservice.security.service.UserDetailService;
import com.example.userservice.security.utils.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-service/")
public class UserController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtil;

    @Autowired
    private UserDetailService userDetailService;

    public UserController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(new AuthenticationResponse("Incorrect username or password"));
        }

        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authenticationRequest.getUsername());
        Account userInfo = accountRepository.findUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userInfo.getUsername(), userInfo.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (accountRepository.findUserByUsername(user.getAccount().getUsername()) != null) {
            return ResponseEntity.ok(new AuthenticationResponse("Username is already used!"));
        }

        try {
            //Create and Save User

            User tempUser = new User(user.getId(), user.getName(), user.getMail());
            userRepository.save(tempUser);

            //Create and Save Account
            user.getAccount().setUser(userRepository.findByID(tempUser.getId()));
            accountRepository.save(user.getAccount());

            return ResponseEntity.ok(new AuthenticationResponse("Registered", user.getAccount().getUsername(), tempUser.getId()));
        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.ok(new AuthenticationResponse("Email or username or name is already use", "none", 0));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new AuthenticationResponse("Sum Ting Wong!"));
        }
    }

    @GetMapping("/user/{UserID}")
    public User findByID(@PathVariable("UserID") Integer id) {
        Account user = accountRepository.findByID(id);
        user.setPassword(null);
        return userRepository.findByID(user.getId());
    }

    
}
