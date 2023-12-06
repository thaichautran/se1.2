package com.memorise.memorise_backend.service;

import com.memorise.memorise_backend.entity.Role;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.AuthenticationServiceImp;
import com.memorise.memorise_backend.payload.request.SignUpRequest;
import com.memorise.memorise_backend.repository.RoleRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticationService implements AuthenticationServiceImp {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public boolean isSignUp(SignUpRequest signUpRequest) {
        boolean isSuccess = false;
        User user = new User();
        int roleId = signUpRequest.getRoleId();
        Optional<Role> role = roleRepository.findById(roleId);

        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        if(role.isPresent()){
            user.setRole(role.get());
        }

        userRepository.save(user);
        isSuccess = true;

        return isSuccess;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
//        System.out.println(user.getUsername() + " " + user.getPassword());
        return passwordEncoder.matches(password, user.getPassword());
    }
}
