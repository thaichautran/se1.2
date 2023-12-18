package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.ImageDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageServiceImp {
    List<ImageDTO> getAllImages();
    ImageDTO updateFavouriteImage(int id);
    List<ImageDTO> getFavouriteImages();

    Resource downloadImage(String url);


}
