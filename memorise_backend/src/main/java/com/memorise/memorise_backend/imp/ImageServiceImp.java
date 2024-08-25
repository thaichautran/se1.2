package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.payload.request.UpdateImageRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageServiceImp {
    ImageDTO updateFavouriteImage(int id, boolean status);
    ImageDTO moveImageToTrashBin(int id, boolean status);
    ImageDTO updateImage(UpdateImageRequest updateImageRequest);
    List<ImageDTO> getAllImages();
    List<ImageDTO> getFavouriteImages();
    List<ImageDTO> getImagesFromTrashBin();
    List<ImageDTO> restoreAllImages();
    List<ImageDTO> findImages(String information);
    Resource downloadImage(String url);
    boolean deleteImage(int id);
    boolean deleteAllImages();


}
