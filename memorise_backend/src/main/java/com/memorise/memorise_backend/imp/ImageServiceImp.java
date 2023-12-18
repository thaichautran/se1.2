package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.ImageDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageServiceImp {
    ImageDTO updateFavouriteImage(int id);
    ImageDTO moveImageToTrashBin(int imageId);
    ImageDTO restoreImageFromTrash(int imageId);
    List<ImageDTO> getAllImages();
    List<ImageDTO> getFavouriteImages();
    List<ImageDTO> getImagesFromTrashBin();
    Resource downloadImage(String url);
    boolean deleteImage(int imageId);


}
