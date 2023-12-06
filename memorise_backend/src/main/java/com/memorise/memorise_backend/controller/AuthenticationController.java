package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.entity.Role;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.payload.RespondData;
import com.memorise.memorise_backend.payload.request.SignUpRequest;
import com.memorise.memorise_backend.repository.RoleRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        RespondData respondData = new RespondData();
        respondData.setData("Username is already taken!");
        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            return new ResponseEntity<>(respondData, HttpStatus.BAD_REQUEST);
        }

        System.out.println(signUpRequest.getName());
        System.out.println(signUpRequest.getUsername());
        System.out.println(signUpRequest.getPassword());
        System.out.println(signUpRequest.getRoleId());
        User user = new User();
        int roleId = signUpRequest.getRoleId();
        Optional<Role> role = roleRepository.findById(roleId);

        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));


        if(role != null){
            user.setRole(role.get());
        }


        userRepository.save(user);
        respondData.setData("User registered successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
