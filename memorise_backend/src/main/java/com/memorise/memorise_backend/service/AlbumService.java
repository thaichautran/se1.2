package com.memorise.memorise_backend.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.memorise.memorise_backend.dto.AlbumDTO;
import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Album;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.ImageAlbum;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.entity.key.KeyImageAlbum;
import com.memorise.memorise_backend.imp.AlbumServiceImp;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import com.memorise.memorise_backend.repository.AlbumRepository;
import com.memorise.memorise_backend.repository.ImageAlbumRepository;
import com.memorise.memorise_backend.repository.ImageRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class AlbumService implements AlbumServiceImp {
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ImageAlbumRepository imageAlbumRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public AlbumDTO createAlbum(MultipartFile file, String name, String desc) {
        String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = Integer.parseInt(authenValue.split(" - ")[1]);

        Optional<User> user = userRepository.findById(userId);
        UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setFile(file);
        uploadRequest.setName(name);
        uploadRequest.setDescription(desc);
        uploadRequest.setLocation("avatar");
        ImageDTO imageDTO = cloudinaryService.uploadImage(uploadRequest);

        Album album = new Album();
        album.setName(name);
        album.setDescription(desc);

        Date timeNow = new Date();
        album.setCreateDate(timeNow);
        album.setUpdateDate(timeNow);

        album.setCoverPhoto(imageDTO.getUrl());
        album.setUser(user.get());

        albumRepository.save(album);

        System.out.println("Time now: " + timeNow);
//        int hour = timeNow.getHours();
//        timeNow.setHours(hour - 7);
//        System.out.println("Time now: " + timeNow);
        Album savedAlbum = albumRepository.findByCoverPhotoAndName(imageDTO.getUrl(), name);

        if (savedAlbum == null) {
            System.out.println("Hello");
        }

        int albumId = savedAlbum.getId();
        System.out.println("Id Album = " + albumId);
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(albumId);
        albumDTO.setName(name);
        albumDTO.setDescription(desc);
        albumDTO.setUrl(imageDTO.getUrl());
        albumDTO.setCreateDate(timeNow);
        albumDTO.setUpdateDate(timeNow);

        cloudinaryService.createFolder("album" + "-" + userId + "-" + albumId);


        ImageAlbum imageAlbum = new ImageAlbum();
        KeyImageAlbum keyImageAlbum = new KeyImageAlbum(albumId, imageDTO.getId());
        imageAlbum.setKey(keyImageAlbum);

        imageAlbumRepository.save(imageAlbum);


        return albumDTO;
    }

    @Override
    public boolean addImageToAlbum(int albumId, int imageId) {
        try {
            ImageAlbum imageAlbum = new ImageAlbum();
            KeyImageAlbum keyImageAlbum = new KeyImageAlbum(albumId, imageId);
            imageAlbum.setKey(keyImageAlbum);
            imageAlbumRepository.save(imageAlbum);
            return true;
        } catch (Exception e) {
            System.out.println("Error to add image to album");
        }
        return false;
    }

    @Override
    public boolean uploadImageToAlbum(UploadRequest uploadRequest, int albumId) {
        try {
            String authenValue = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId = Integer.parseInt(authenValue.split(" - ")[1]);

            String folerName = "album-" + userId + "-" + albumId;
            String urlImage = cloudinaryService.uploadImageToFolder(uploadRequest.getFile(), folerName);

            Date originCreatedDate = null;

            try {
                Metadata metadata = ImageMetadataReader.readMetadata(uploadRequest.getFile().getInputStream(), uploadRequest.getFile().getSize());

                // Try to get Exif metadata
                ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if (exifDirectory != null) {
                    Date tempDate = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    int hour = tempDate.getHours();
//                System.out.println("Hour: " + hour);
                    tempDate.setHours(hour - 7);
//                System.out.println("Temp date after: " + tempDate);
                    originCreatedDate = tempDate;
//                System.out.println("Origin date: " + originCreatedDate);
                } else {
                    originCreatedDate = new Date();
                }
            } catch (Exception e) {
                System.out.println("Error to get created date");
            }

//        Curent date (updateDate)
            Date updateDate = new Date();

//            System.out.println("url: " + urlImage);
            Image image = new Image();

            image.setUrl(urlImage);
            image.setName(uploadRequest.getName());
            image.setDescription(uploadRequest.getDescription());
            image.setLocation(uploadRequest.getLocation());
            image.setSharingUrl(urlImage);
            image.setCreateDate(originCreatedDate);
            image.setUpdateDate(updateDate);
            Optional<User> user = userRepository.findById(userId);
            if (user != null) {
                image.setUser(user.get());
            }
            imageRepository.save(image);
            Image image1 = imageRepository.findTopByUrlOrderByUpdateDateDesc(urlImage);

            int imageId = image1.getId();
//            System.out.println("Id = " + imageId);
//            System.out.println("albumId = " + albumId);

            ImageAlbum imageAlbum = new ImageAlbum();
            KeyImageAlbum keyImageAlbum = new KeyImageAlbum(albumId, imageId);
            imageAlbum.setKey(keyImageAlbum);
            imageAlbumRepository.save(imageAlbum);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error to upload image to album");
        }
        return false;
    }

    @Override
    public List<ImageDTO> getImagesInAlbum(int albumId) {
        List<ImageAlbum> imageAlbums = imageAlbumRepository.findByKeyAlbumId(albumId);

        List<Image> images = new ArrayList<>();
        for (ImageAlbum imageAlbum : imageAlbums) {
            images.add(imageAlbum.getImage());
        }

        List<ImageDTO> imageDTOS = new ArrayList<>();
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

    @Override
    public boolean deleteAlbum(int albumId) {
        try {

            List<ImageAlbum> imageAlbums = imageAlbumRepository.findByKeyAlbumId(albumId);
            imageAlbumRepository.deleteAll(imageAlbums);

            albumRepository.deleteById(albumId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<AlbumDTO> getAlbums() {
        List<Album> albums = albumRepository.findAll();
        List<AlbumDTO> albumDTOS = new ArrayList<>();

        for (Album album : albums){
            AlbumDTO albumDTO = new AlbumDTO();
            albumDTO.setId(album.getId());
            albumDTO.setName(album.getName());
            albumDTO.setUrl(album.getCoverPhoto());
            albumDTO.setDescription(album.getDescription());
            albumDTO.setCreateDate(album.getCreateDate());
            albumDTO.setUpdateDate(album.getUpdateDate());

            albumDTOS.add(albumDTO);
        }
        return albumDTOS;
    }
}
