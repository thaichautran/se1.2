package com.memorise.memorise_backend.service;



import com.cloudinary.Cloudinary;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.imp.CloudinaryServiceImp;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import com.memorise.memorise_backend.repository.ImageRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//
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
        Date originCreatedDate = null;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(uploadRequest.getFile().getInputStream(), uploadRequest.getFile().getSize());

            // Try to get Exif metadata
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                Date creationDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = dateFormat.format(creationDate);

                originCreatedDate = dateFormat.parse(formattedDate);
                System.out.println("Origin date " + originCreatedDate);
            } else {
                System.out.println("No Exif metadata found in the uploaded image.");
                // Handle the case when Exif metadata is not present
            }
        } catch (Exception e) {
            System.out.println("Error to get created date");
        }

        Date updateDate = new Date();
        System.out.println("Current date: " + updateDate);


        try {
            Map data = upload(uploadRequest.getFile());

            String url = (String) data.get("url");
            Image image = new Image();

            image.setUrl(url);
            image.setName(uploadRequest.getName());
            image.setDescription(uploadRequest.getDescription());
            image.setLocation(uploadRequest.getLocation());
            image.setSharingUrl(url);
            image.setCreateDate(originCreatedDate);
            image.setUpdateDate(updateDate);
            Optional<User> user = userRepository.findById(uploadRequest.getUserId());
            if (user != null) {
                image.setUser(user.get());
            }
            imageRepository.save(image);

            return new ImageDTO(url, uploadRequest.getName(), uploadRequest.getLocation(), uploadRequest.getDescription(), uploadRequest.getUserId(), originCreatedDate, updateDate);
        } catch (Exception e) {
            System.out.println("Error to upload file");
            return null;
        }
    }

    //    Test other parameters
    @Override
    public ImageDTO isUploadImage(MultipartFile file, UploadRequest uploadRequest) {
        Date originCreatedDate = null;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(uploadRequest.getFile().getInputStream(), uploadRequest.getFile().getSize());

            // Try to get Exif metadata
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                Date creationDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = dateFormat.format(creationDate);

                originCreatedDate = dateFormat.parse(formattedDate);
                System.out.println("Origin date " + originCreatedDate);
            } else {
                System.out.println("No Exif metadata found in the uploaded image.");
                // Handle the case when Exif metadata is not present
            }
        } catch (Exception e) {
            System.out.println("Error to get created date");
        }

        Date updateDate = new Date();
        System.out.println("Current date: " + updateDate);
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
            return new ImageDTO(url, uploadRequest.getName(), uploadRequest.getLocation(), uploadRequest.getDescription(), uploadRequest.getUserId(), originCreatedDate, updateDate);
        } catch (Exception e) {
            System.out.println("Error to upload file");
            return null;
        }
    }

    @Override
    public ImageDTO isUploadImage(MultipartFile file, String name, String location, String description, int userId) {
        Date originCreatedDate = null;
        Metadata metadata = null;

        try {
            metadata = ImageMetadataReader.readMetadata(file.getInputStream(), file.getSize());
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.format("[%s] - %s = %s", directory.getName(), tag.getTagName(), tag.getDescription());
                    System.out.println();
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                        System.err.println();
                    }
                }
            }
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Try to get Exif metadata
        ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifDirectory != null) {
            Date creationDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            // Format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formattedDate = dateFormat.format(creationDate);

            try {
                originCreatedDate = dateFormat.parse(formattedDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Origin date " + originCreatedDate);
        } else {
            System.out.println("No Exif metadata found in the uploaded image.");
            // Handle the case when Exif metadata is not present
        }
        Date updateDate = new Date();
        System.out.println("Current date: " + updateDate);
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
            return new ImageDTO(url, name, location, description, userId, originCreatedDate, updateDate);
        } catch (Exception e) {
            System.out.println("Error to upload file");
            return null;
        }
    }


}
