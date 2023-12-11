package com.memorise.memorise_backend.service;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import java.util.*;

@Service
public class CloudinaryService implements CloudinaryServiceImp {
    @Autowired
    Cloudinary cloudinary;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    public String uploadFileVideo(MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);

            // Set the resource type parameter
            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("resource_type", "video"); // Specify "video" or "auto"

            // Use uploadLarge for videos
            Map<String, Object> uploadResult = cloudinary.uploader().uploadLarge(uploadedFile, uploadParams);
//
//            boolean isDeleted = uploadedFile.delete();
//
//            if (isDeleted) {
//                System.out.println("File successfully deleted");
//            } else {
//                System.out.println("File doesn't exist");
//            }
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace(); // Print the full stack trace for debugging
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

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
    public ImageDTO uploadImage(UploadRequest uploadRequest) {
        Date originCreatedDate = null;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(uploadRequest.getFile().getInputStream(), uploadRequest.getFile().getSize());

            // Try to get Exif metadata
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                originCreatedDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            } else {
                originCreatedDate = new Date();
            }
        } catch (Exception e) {
            System.out.println("Error to get created date");
        }

//        Curent date (updateDate)
        Date updateDate = new Date();

        try {
            Map data = upload(uploadRequest.getFile());
            String url = (String) data.get("url");

//            String url = uploadFile(uploadRequest.getFile());
            System.out.println("url: " + url);
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
            e.printStackTrace();
            System.out.println("Error to upload file");
            return null;
        }
    }

    @Override
    public ImageDTO uploadVideo(UploadRequest uploadRequest) {
        Date originCreatedDate = null;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(uploadRequest.getFile().getInputStream(), uploadRequest.getFile().getSize());

            // Try to get Exif metadata
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                originCreatedDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            } else {
                originCreatedDate = new Date();
            }
        } catch (Exception e) {
            System.out.println("Error to get created date");
        }

//        Curent date (updateDate)
        Date updateDate = new Date();

        try {
            String url = uploadFileVideo(uploadRequest.getFile());
            System.out.println("url: " + url);
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
            e.printStackTrace();
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
                System.out.println("Creation date: " + creationDate);
                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = dateFormat.format(creationDate);

                originCreatedDate = dateFormat.parse(formattedDate);
                System.out.println("Origin date " + originCreatedDate);
            } else {
                originCreatedDate = new Date();
                System.out.println("No Exif metadata found in the uploaded image.");
                // Handle the case when Exif metadata is not present
            }
        } catch (Exception e) {
            System.out.println("Error to get created date");
        }

        Date updateDate = new Date();
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

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream(), file.getSize());

            // Try to get Exif metadata
            ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                Date creationDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                System.out.println("Creation date: " + creationDate);
                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = dateFormat.format(creationDate);

                originCreatedDate = dateFormat.parse(formattedDate);
                System.out.println("Origin date " + originCreatedDate);
            } else {
                originCreatedDate = new Date();
                System.out.println("No Exif metadata found in the uploaded image.");
                // Handle the case when Exif metadata is not present
            }
        } catch (Exception e) {
            System.out.println("Error to get created date");
        }

        Date updateDate = new Date();
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
