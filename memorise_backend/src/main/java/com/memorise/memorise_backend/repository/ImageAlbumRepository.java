package com.memorise.memorise_backend.repository;

import com.memorise.memorise_backend.entity.ImageAlbum;
import com.memorise.memorise_backend.entity.key.KeyImageAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageAlbumRepository extends JpaRepository<ImageAlbum, KeyImageAlbum> {
}
