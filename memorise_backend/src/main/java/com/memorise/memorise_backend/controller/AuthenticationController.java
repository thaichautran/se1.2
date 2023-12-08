package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.dto.TokenDTO;
import com.memorise.memorise_backend.entity.Role;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.AuthenticationServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import com.memorise.memorise_backend.payload.request.LoginRequest;
import com.memorise.memorise_backend.payload.request.SignUpRequest;
import com.memorise.memorise_backend.repository.RoleRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import com.memorise.memorise_backend.utils.JwtUtilsHelper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")

public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationServiceImp authenticationServiceImp;

    @Autowired
    JwtUtilsHelper jwtUtilsHelper;



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

        if(signUpRequest.getUsername() == null || signUpRequest.getName() == null || signUpRequest.getName() == null){
            respondData.setStatus(403);
            respondData.setSuccess(false);
            respondData.setDesc("Request receives wrong parameters");
            respondData.setData("Check again parameter name: username - password - name");

            return new ResponseEntity<>(respondData, HttpStatus.FORBIDDEN);
        }

        respondData.setDesc("User registered successfully");
        respondData.setData(authenticationServiceImp.isSignUp(signUpRequest));
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PostMapping("/check_login")
    public ResponseEntity<?> checkLogin(@RequestBody LoginRequest loginRequest){

//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String encrypted = Encoders.BASE64.encode(secretKey.getEncoded());
//        System.out.println(encrypted);

        RespondData respondData = new RespondData();

        if(loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            respondData.setStatus(403);
            respondData.setSuccess(false);
            respondData.setDesc("Paramater receives wrong parameters");
            respondData.setData("Check again parameter name: username - password");

            return new ResponseEntity<>(respondData, HttpStatus.FORBIDDEN);
        }

        if(!userRepository.existsByUsername(loginRequest.getUsername())){
            respondData.setStatus(400);
            respondData.setSuccess(false);
            respondData.setDesc("Request is not valid");
            respondData.setData("Username does not exist");

            return new ResponseEntity<>(respondData, HttpStatus.BAD_REQUEST);
        }

        if(!authenticationServiceImp.checkLogin(loginRequest.getUsername(), loginRequest.getPassword())){
            respondData.setStatus(401);
            respondData.setSuccess(false);
            respondData.setDesc("Request is not valid");
            respondData.setData("Password is not true");


            return new ResponseEntity<>(respondData, HttpStatus.UNAUTHORIZED);
        }
        respondData.setDesc("Login successfully");
        String token = jwtUtilsHelper.generateToken(loginRequest.getUsername());
        TokenDTO tokeDTO = new TokenDTO(token);
        respondData.setData(tokeDTO);

        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PostMapping("/forget_password")
    public ResponseEntity<?> forgetPassword(@RequestParam String username){
        RespondData respondData = new RespondData();
        User user = userRepository.findByUsername(username);
        if(user == null){
            respondData.setStatus(400);
            respondData.setSuccess(false);
            respondData.setDesc("Request is not valid");
            respondData.setData("Username does not exist");

            return new ResponseEntity<>(respondData, HttpStatus.BAD_REQUEST);
        }
        String otp = authenticationServiceImp.generateOtp();
        boolean isUpdateOtp = authenticationServiceImp.updateResetPasswordOtp(username, otp);
        boolean isSendMail = authenticationServiceImp.mailSender(username, otp);

        if(!isSendMail){
            respondData.setStatus(403);
            respondData.setSuccess(false);
            respondData.setDesc("Request is fobbiden");
            respondData.setData("Can not send OTP to your email, Please check again smtp configuration in backend side");


            return new ResponseEntity<>(respondData, HttpStatus.FORBIDDEN);
        }

        respondData.setDesc("Request is successfull");
        respondData.setData("OTP is sent to your email, please check!");

        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }



}
