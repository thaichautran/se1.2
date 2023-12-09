package com.memorise.memorise_backend.service;

import com.cloudinary.Cloudinary;
import com.memorise.memorise_backend.imp.CloudinaryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService implements CloudinaryServiceImp {
    @Autowired
    Cloudinary cloudinary;

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

    public Map upload(MultipartFile file)  {
        try{
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }
}
