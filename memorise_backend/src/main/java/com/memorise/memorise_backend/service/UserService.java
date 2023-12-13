package com.memorise.memorise_backend.service;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.UserServiceImp;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUserDTO() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setName(user.getName());
            userDTO.setAvatar(user.getAvatar());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public User getUserByOtp(String otp) {
        return userRepository.findByOtp(otp);
    }

    @Override
    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setRoleId(user.getRole().getId());

        return userDTO;
    }

    @Override
    public UserDTO getUserDTO(int id) {
        Optional<User> user = userRepository.findById(id);
        UserDTO userDTO = new UserDTO();
        Date createdDate = null;
        Date createDate = user.get().getCreateDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = dateFormat.format(createDate);
        System.out.println(formattedDate);

        try {
            createdDate = dateFormat.parse(formattedDate);
            System.out.println(createdDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        List<ImageDTO> listImg = new ArrayList<>();
        for (Image img : user.get().getListImage()) {
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setName(img.getName());
            imageDTO.setLocation(img.getLocation());
            imageDTO.setDescription(img.getDescription());
            imageDTO.setId(img.getId());
            imageDTO.setUrl(img.getUrl());

            listImg.add(imageDTO);
        }

        userDTO.setImageDTOS(listImg);
        userDTO.setCreateDate(createdDate);
        return userDTO;
    }


}
