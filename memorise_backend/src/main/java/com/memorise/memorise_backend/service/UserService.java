package com.memorise.memorise_backend.service;

import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.UserServiceImp;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserDTO> getAllUserDTO() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user : users){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setName(user.getName());
            userDTO.setAvatar(user.getAvatar());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
}
