package com.memorise.memorise_backend.service;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.ImageServiceImp;
import com.memorise.memorise_backend.repository.ImageRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements ImageServiceImp {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public List<ImageDTO> getAllImages() {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);

        List<ImageDTO> imageDTOS = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        if (user != null) {
            for (Image img : user.get().getListImage()) {
                ImageDTO imageDTO = new ImageDTO();

                imageDTO.setId(img.getId());
                imageDTO.setUrl(img.getUrl());
                imageDTO.setDescription(img.getDescription());
                imageDTO.setName(img.getName());
                imageDTO.setFavourite(img.isFavourite());
                imageDTO.setLocation(img.getLocation());
                imageDTO.setPublic(img.isPublic());
                imageDTO.setRemove(img.isRemove());
                imageDTO.setCreateDate(img.getCreateDate());
                imageDTO.setUpdateDate(img.getUpdateDate());

                imageDTOS.add(imageDTO);
            }
            return imageDTOS;
        }
        return null;
    }

    @Override
    public ImageDTO updateFavouriteImage(int id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image != null) {
            Image img = image.get();
            img.setFavourite(true);

            Date now = new Date();
            img.setUpdateDate(now);

            imageRepository.save(img);

            ImageDTO imageDTO = new ImageDTO();

            imageDTO.setId(img.getId());
            imageDTO.setUrl(img.getUrl());
            imageDTO.setName(img.getName());
            imageDTO.setLocation(img.getLocation());
            imageDTO.setDescription(img.getDescription());
            imageDTO.setCreateDate(img.getCreateDate());
            imageDTO.setUpdateDate(img.getUpdateDate());
            imageDTO.setFavourite(img.isFavourite());
            imageDTO.setPublic(img.isPublic());
            imageDTO.setRemove(img.isRemove());

            return imageDTO;
        }
        return null;
    }

    @Override
    public List<ImageDTO> getFavouriteImages() {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);
        Optional<User> user = userRepository.findById(userId);
        if(user != null){
            List<ImageDTO> imageDTOS = new ArrayList<>();
            List<Image> images = imageRepository.findByFavouriteAndUser(true, user.get());
            for (Image img : images) {
                ImageDTO imageDTO = new ImageDTO();

                imageDTO.setId(img.getId());
                imageDTO.setUrl(img.getUrl());
                imageDTO.setName(img.getName());
                imageDTO.setLocation(img.getLocation());
                imageDTO.setDescription(img.getDescription());
                imageDTO.setCreateDate(img.getCreateDate());
                imageDTO.setUpdateDate(img.getUpdateDate());
                imageDTO.setFavourite(img.isFavourite());
                imageDTO.setPublic(img.isPublic());
                imageDTO.setRemove(img.isRemove());

                imageDTOS.add(imageDTO);
            }
            return imageDTOS;
        }
        return null;
    }


}
