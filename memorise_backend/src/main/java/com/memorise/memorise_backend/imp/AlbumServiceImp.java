package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.AlbumDTO;
import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumServiceImp {
    AlbumDTO createAlbum(MultipartFile file, String desc, String name);
    boolean addImageToAlbum(int albumId, int imageId);
    boolean uploadImageToAlbum(UploadRequest uploadRequest, int albumId);
    List<ImageDTO> getImagesInAlbum(int albumId);
    boolean deleteAlbum(int albumId);
    List<AlbumDTO> getAlbums();
    boolean removeImageFromAlbum(int albumId, int imageId);



}
