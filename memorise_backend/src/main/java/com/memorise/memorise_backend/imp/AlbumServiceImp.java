package com.memorise.memorise_backend.imp;

import com.memorise.memorise_backend.dto.AlbumDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AlbumServiceImp {
    AlbumDTO createAlbum(MultipartFile file, String desc, String name);

}
