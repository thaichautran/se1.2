package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.imp.UserServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        RespondData respondData = new RespondData();
        respondData.setData(userServiceImp.getAllUserDTO());
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
