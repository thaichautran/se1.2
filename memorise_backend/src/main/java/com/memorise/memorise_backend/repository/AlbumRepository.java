package com.memorise.memorise_backend.repository;

import com.memorise.memorise_backend.entity.Album;
import com.memorise.memorise_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    Album findByCoverPhotoAndName(String url, String name);
    List<Album> findByUser(User user);
}
