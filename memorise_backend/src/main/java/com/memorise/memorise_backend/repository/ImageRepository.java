package com.memorise.memorise_backend.repository;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByUrl(String url);
    Image findTopByUrlOrderByUpdateDateDesc(String url);

    List<Image> findByFavouriteAndUser(boolean isFovourite, User user);
}
