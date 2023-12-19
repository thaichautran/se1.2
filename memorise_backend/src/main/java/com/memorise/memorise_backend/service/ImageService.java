package com.memorise.memorise_backend.service;

import com.cloudinary.Url;
import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.ImageServiceImp;
import com.memorise.memorise_backend.payload.request.UpdateImageRequest;
import com.memorise.memorise_backend.repository.ImageRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
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
                if (img.isRemove() == false) {
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
            }
            return imageDTOS;
        }
        return null;
    }

    @Override
    public ImageDTO updateFavouriteImage(int id, boolean status) {
        Optional<Image> image = imageRepository.findById(id);
        if (image != null) {
            Image img = image.get();
            img.setFavourite(status);

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
        if (user != null) {
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

    @Override
    public Resource downloadImage(String url) {
        try {
            UrlResource resource = new UrlResource(url);
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
        } catch (Exception e) {
            System.out.println("Can't download file");
        }
        return null;
    }

    @Override
    public ImageDTO moveImageToTrashBin(int id, boolean status) {
        Optional<Image> image = imageRepository.findById(id);
        if (image != null) {
            Image img = image.get();
            img.setRemove(status);
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
    public ImageDTO updateImage(UpdateImageRequest updateImageRequest) {
        Optional<Image> image = imageRepository.findById(updateImageRequest.getId());
        if(image != null){
            Image img = image.get();
            img.setName(updateImageRequest.getName());
            img.setDescription(updateImageRequest.getDescription());
            img.setLocation(updateImageRequest.getLocation());
            img.setUpdateDate(new Date());

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
    public List<ImageDTO> getImagesFromTrashBin() {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);
        Optional<User> user = userRepository.findById(userId);

        List<ImageDTO> imageDTOS = new ArrayList<>();
        if (user != null) {
            List<Image> images = imageRepository.findByIsRemoveAndUser(true, user.get());
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
        }
        return imageDTOS;
    }

    @Override
    public List<ImageDTO> restoreAllImages() {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);
        Optional<User> user = userRepository.findById(userId);

        List<ImageDTO> imageDTOS = new ArrayList<>();
        if (user != null) {
            List<Image> images = imageRepository.findByIsRemoveAndUser(true, user.get());
            for(Image img : images){
                img.setRemove(false);

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
            imageRepository.saveAll(images);

        }
        return imageDTOS;
    }

    @Override
    public List<ImageDTO> findImages(String information) {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);
        Optional<User> user = userRepository.findById(userId);

        List<Image> images = imageRepository.findByUserAndLocationContainingOrUserAndNameContaining(
                user.get(), information, user.get(),information);
        List<ImageDTO> imageDTOS = new ArrayList<>();
        if(images.size() > 0){
            for(Image img : images){

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
        }
        return imageDTOS;
    }

    @Override
    public boolean deleteImage(int id) {
        try {
            imageRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAllImages() {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);
        Optional<User> user = userRepository.findById(userId);

        if (user != null) {
            List<Image> images = imageRepository.findByIsRemoveAndUser(true, user.get());
            imageRepository.deleteAll(images);
            return true;
        }
        return false;
    }

}
