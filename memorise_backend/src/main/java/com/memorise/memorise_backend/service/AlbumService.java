package com.memorise.memorise_backend.service;

import com.memorise.memorise_backend.dto.AlbumDTO;
import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Album;
import com.memorise.memorise_backend.entity.ImageAlbum;
import com.memorise.memorise_backend.entity.User;
import com.memorise.memorise_backend.entity.key.KeyImageAlbum;
import com.memorise.memorise_backend.imp.AlbumServiceImp;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import com.memorise.memorise_backend.repository.AlbumRepository;
import com.memorise.memorise_backend.repository.ImageAlbumRepository;
import com.memorise.memorise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

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

        if(savedAlbum == null){
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
}
