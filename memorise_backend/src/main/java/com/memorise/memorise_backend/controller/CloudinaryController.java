package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.imp.CloudinaryServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {
    @Autowired
    CloudinaryServiceImp cloudinaryServiceImp;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadImage(@ModelAttribute UploadRequest uploadRequest){
        RespondData respondData = new RespondData();
        ImageDTO imageDTO = cloudinaryServiceImp.isUploadImage(uploadRequest);

        respondData.setDesc("Upload file successfully!");
        respondData.setData(imageDTO);
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

//    Test API with other parameters

//    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE,
//                                                    MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> uploadImage(@RequestPart MultipartFile file, @RequestPart UploadRequest uploadRequest){
//        RespondData respondData = new RespondData();
//        boolean isUploadImage = cloudinaryServiceImp.isUploadImage(file, uploadRequest);
//
//        respondData.setData(isUploadImage);
//        return new ResponseEntity<>(respondData, HttpStatus.OK);
//    }




//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file,
//                                         @RequestParam String name,
//                                         @RequestParam String location,
//                                         @RequestParam String description,
//                                         @RequestParam int userId){
//        RespondData respondData = new RespondData();
//        boolean isUploadImage = cloudinaryServiceImp.isUploadImage(file, name, location, description, userId);
//
//        respondData.setData(isUploadImage);
//        return new ResponseEntity<>(respondData, HttpStatus.OK);
//    }
}
