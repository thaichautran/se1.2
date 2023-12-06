package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.entity.Role;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.AuthenticationServiceImp;
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
    UserRepository userRepository;

    @Autowired
    AuthenticationServiceImp authenticationServiceImp;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        RespondData respondData = new RespondData();

        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            respondData.setStatus(400);
            respondData.setSuccess(false);
            respondData.setDesc("Request is not valid");
            respondData.setData("Username is already taken!");

            return new ResponseEntity<>(respondData, HttpStatus.BAD_REQUEST);
        }

        respondData.setDesc("User registered successfully");
        respondData.setData(authenticationServiceImp.isSignUp(signUpRequest));
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
