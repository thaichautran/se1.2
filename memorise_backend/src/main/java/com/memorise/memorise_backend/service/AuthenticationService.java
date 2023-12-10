package com.memorise.memorise_backend.service;

import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.entity.Role;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.AuthenticationServiceImp;
import com.memorise.memorise_backend.imp.UserServiceImp;
import com.memorise.memorise_backend.payload.request.SignUpRequest;
import com.memorise.memorise_backend.repository.RoleRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService implements AuthenticationServiceImp {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

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


    @Override
    public boolean updateResetPasswordOtp(String username, String otp) {
        User user = userRepository.findByUsername(username);
        if(user != null){
            user.setOtp(otp);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        try{
            user.setPassword(encodedPassword);
            user.setOtp(null);
            userRepository.save(user);
            return true;
        } catch (Exception e){
            System.out.println("Can not reset password");
            return false;
        }
    }

    @Override
    public String generateOtp() {
        Random rnd = new Random();
        int number = rnd.nextInt(111111, 1000000);
        return String.format("%06d", number);
    }

    @Override
    public boolean mailSender(String username, String otp) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("memorise@gmail.com", "Memorise Support");
            helper.setTo(username);
            String subject = "Here is the OTP to reset your password";
            String contentOtp = "Your OTP to reset your password is: " + otp;
            helper.setSubject(subject);
            helper.setText(contentOtp);

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Error to send email");
            return false;
        }
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(username);
//        System.out.println("username: " + username);
//        message.setSubject("Here is the OTP to reset your password");
//        message.setText("Your OTP to reset your password is: " + otp);
//        javaMailSender.send(message);
//        return true;
    }
}
