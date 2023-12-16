package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.dto.AlbumDTO;
import com.memorise.memorise_backend.imp.AlbumServiceImp;
import com.memorise.memorise_backend.imp.CloudinaryServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    CloudinaryServiceImp cloudinaryServiceImp;

    @Autowired
    AlbumServiceImp albumServiceImp;

    @PostMapping("/create_album")
    public ResponseEntity<?> createFolder(@RequestParam MultipartFile file, @RequestParam String name, @RequestParam String desc){
        RespondData respondData = new RespondData();
        AlbumDTO albumDTO = albumServiceImp.createAlbum(file, name, desc);
        respondData.setData(albumDTO);
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImageToFolder(@RequestParam MultipartFile file, @RequestParam String folderName){
        RespondData respondData = new RespondData();
        cloudinaryServiceImp.uploadImageToFolder(file, folderName);
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
