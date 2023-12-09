package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.payload.request.SignUpRequest;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;

public interface AuthenticationServiceImp {
    boolean isSignUp(SignUpRequest signUpRequest);
    boolean checkLogin(String username, String password);
    boolean updateResetPasswordOtp(String username, String otp);
    boolean resetPassword(User user, String newPassword);
    String generateOtp();
    boolean mailSender(String username, String otp);


}
