package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.dto.UserDTO;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryServiceImp {
    Map upload(MultipartFile file);
    ImageDTO uploadImage(UploadRequest uploadRequest);
    ImageDTO uploadVideo(UploadRequest uploadRequest);
    boolean isUploadImage(MultipartFile file, UploadRequest uploadRequest);
    boolean isUploadImage(MultipartFile file, String name, String location, String description);
    void createFolder(String folderName);
    String uploadImageToFolder(MultipartFile imageFile, String folderName);
}
