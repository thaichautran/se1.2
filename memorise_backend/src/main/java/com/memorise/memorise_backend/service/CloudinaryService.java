package com.memorise.memorise_backend.service;

import com.cloudinary.Cloudinary;
import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.CloudinaryServiceImp;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import com.memorise.memorise_backend.repository.ImageRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CloudinaryService implements CloudinaryServiceImp {
    @Autowired
    Cloudinary cloudinary;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

//    @Override
//    public String upload(MultipartFile file)  {
//        try {
//            return this.cloudinary.uploader().upload(file.getBytes(),
//                            Map.of("public_id", UUID.randomUUID().toString()))
//                    .get("url")
//                    .toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public Map upload(MultipartFile file) {
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        } catch (IOException io) {
            throw new RuntimeException("Image upload fail");
        }
    }

    @Override
    public ImageDTO isUploadImage(UploadRequest uploadRequest) {
        try {
            Map data = upload(uploadRequest.getFile());

            String url = (String) data.get("url");
            Image image = new Image();

            image.setUrl(url);
            image.setName(uploadRequest.getName());
            image.setDescription(uploadRequest.getDescription());
            image.setLocation(uploadRequest.getLocation());
            image.setSharingUrl(url);
            Optional<User> user = userRepository.findById(uploadRequest.getUserId());
            if (user != null) {
                image.setUser(user.get());
            }
            imageRepository.save(image);

            return new ImageDTO(url, uploadRequest.getName(), uploadRequest.getLocation(),uploadRequest.getDescription(), uploadRequest.getUserId());
        } catch (Exception e) {
            System.out.println("Error to upload file");
            return null;
        }
    }

//    Test other parameters
    @Override
    public ImageDTO isUploadImage(MultipartFile file, UploadRequest uploadRequest) {
        try {
            Map data = upload(file);

            String url = (String) data.get("url");
            Image image = new Image();

            image.setUrl(url);
            image.setName(uploadRequest.getName());
            image.setDescription(uploadRequest.getDescription());
            image.setLocation(uploadRequest.getLocation());
            image.setSharingUrl(url);
            Optional<User> user = userRepository.findById(uploadRequest.getUserId());

            if (user != null) {
                image.setUser(user.get());
            }

            imageRepository.save(image);
            return new ImageDTO(url, uploadRequest.getName(), uploadRequest.getLocation(),uploadRequest.getDescription(), uploadRequest.getUserId());
        } catch (Exception e) {
            System.out.println("Error to upload file");
            return null;
        }
    }

    @Override
    public ImageDTO isUploadImage(MultipartFile file, String name, String location, String description, int userId) {
        try {
            Map data = upload(file);

            String url = (String) data.get("url");
            Image image = new Image();

            image.setUrl(url);
            image.setName(name);
            image.setDescription(description);
            image.setLocation(location);
            image.setSharingUrl(url);
            Optional<User> user = userRepository.findById(userId);
            if (user != null) {
                image.setUser(user.get());
            }
            imageRepository.save(image);
            return new ImageDTO(url, name, location, description, userId);
        } catch (Exception e) {
            System.out.println("Error to upload file");
            return null;
        }
    }
}
