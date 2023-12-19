package com.memorise.memorise_backend.repository;

import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.ImageAlbum;
import com.memorise.memorise_backend.entity.key.KeyImageAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageAlbumRepository extends JpaRepository<ImageAlbum, KeyImageAlbum> {
    List<ImageAlbum> findByKeyAlbumId(int albumId);
    ImageAlbum findByKeyAlbumIdAndImageId(int albumId, int imageId);
}
