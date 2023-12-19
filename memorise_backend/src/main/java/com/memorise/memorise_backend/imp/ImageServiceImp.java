package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.ImageDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageServiceImp {
    ImageDTO updateFavouriteImage(int id, boolean status);
    ImageDTO moveImageToTrashBin(int id, boolean status);
    List<ImageDTO> getAllImages();
    List<ImageDTO> getFavouriteImages();
    List<ImageDTO> getImagesFromTrashBin();
    List<ImageDTO> restoreAllImages();
    Resource downloadImage(String url);
    boolean deleteImage(int id);
    boolean deleteAllImages();


}
