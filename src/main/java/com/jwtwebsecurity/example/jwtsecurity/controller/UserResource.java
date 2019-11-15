package com.jwtwebsecurity.example.jwtsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;

import com.jwtwebsecurity.example.jwtsecurity.message.request.LoginForm;
import com.jwtwebsecurity.example.jwtsecurity.message.request.SignUpForm;
import com.jwtwebsecurity.example.jwtsecurity.message.response.JwtResponse;
import com.jwtwebsecurity.example.jwtsecurity.model.User;
import com.jwtwebsecurity.example.jwtsecurity.repository.UserRepository;
import com.jwtwebsecurity.example.jwtsecurity.security.jwt.JwtProvider;
import com.jwtwebsecurity.example.jwtsecurity.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api")
public class UserResource {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/register")
    // @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody SignUpForm signUpRequest) {
        // if (!checkPasswordLength(managedUserVM.getPassword())) {
        //     throw new InvalidPasswordException();
        // }
        User user = userService.registerUser(signUpRequest);
        // mailService.sendActivationEmail(user);
    }


    @PostMapping("/user/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok().body(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
    

    @GetMapping("/account")
	public ResponseEntity<Optional<User>> getCurrentUser() {

        // userService.getCurrentUserLogin().flatMap(userRepository::findByUsername);

		return ResponseEntity.ok().body(userService.getCurrentUserLogin().flatMap(userRepository::findByUsername));
	}

}