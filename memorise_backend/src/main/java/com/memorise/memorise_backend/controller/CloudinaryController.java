package com.memorise.memorise_backend.controller;

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

import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {
    @Autowired
    CloudinaryServiceImp cloudinaryServiceImp;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile image,
                                         @RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam String location,
                                         @RequestParam int user_id){
        RespondData respondData = new RespondData();
        Map data = this.cloudinaryServiceImp.upload(image);
        System.out.println(data.get("url"));
        respondData.setData(data.get("url"));
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
