package com.aru.controllers;

import com.aru.configuration.JwtProvider;
import com.aru.models.User;
import com.aru.repositories.UserRepository;
import com.aru.requests.LoginRequest;
import com.aru.responses.AuthResponse;
import com.aru.services.CustomUserDetailsImpl;
import com.aru.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;


    @Autowired
    private SubscriptionService subscriptionService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User isUserExist = userRepository.findByEmail(user.getEmail());

        if (isUserExist!=null){
            throw new Exception("Email is already exist!");
        }

        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createdUser);

        //Adding Subscription
        subscriptionService.createSubscription(savedUser);

        //After creating user create the Auth
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the Token
        String jwt = JwtProvider.generateToken(authentication);

        // Create Auth Response
        AuthResponse res = new AuthResponse();
        res.setMessage("Successfully Signed-up");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest){

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        //After logged-in user check the Auth
        Authentication authentication = authenticated(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the Token
        String jwt = JwtProvider.generateToken(authentication);

        // Create Auth Response
        AuthResponse res = new AuthResponse();
        res.setMessage("Successfully Logged-in");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticated(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
