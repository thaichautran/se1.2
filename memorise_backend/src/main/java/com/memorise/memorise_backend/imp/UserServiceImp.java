package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.entity.User;

import java.util.List;

public interface UserServiceImp {
    List<UserDTO> getAllUserDTO();

    User getUserByOtp(String otp);

    UserDTO getUserDTO(User user);

    UserDTO getUserDTO(int id);



}
