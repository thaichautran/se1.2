package com.memorise.memorise_backend.imp;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryServiceImp {
    Map upload(MultipartFile file);
}
