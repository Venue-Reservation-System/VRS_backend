package com.jwtwebsecurity.example.jwtsecurity.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.jwtwebsecurity.example.jwtsecurity.message.request.SignUpForm;
import com.jwtwebsecurity.example.jwtsecurity.model.Role;
import com.jwtwebsecurity.example.jwtsecurity.model.RoleName;
import com.jwtwebsecurity.example.jwtsecurity.model.User;
import com.jwtwebsecurity.example.jwtsecurity.repository.RoleRepository;
import com.jwtwebsecurity.example.jwtsecurity.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public User registerUser(SignUpForm signUpRequest) {
        // if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        // return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already
        // taken!"),
        // HttpStatus.BAD_REQUEST);
        // }

        // if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        // return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in
        // use!"),
        // HttpStatus.BAD_REQUEST);
        // }

        // Creating user's account
        // User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
        // signUpRequest.getEmail(),
        // encoder.encode(signUpRequest.getPassword()));

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),false);

        // Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role r = new Role(RoleName.ROLE_USER);
        roles.add(r);



        user.setRoles(roles);
        userRepository.save(user);

        return  userRepository.save(user);
    }


    public Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

}