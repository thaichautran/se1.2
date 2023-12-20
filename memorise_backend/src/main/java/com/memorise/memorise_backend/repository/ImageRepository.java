package com.memorise.memorise_backend.repository;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.entity.Image;
import com.memorise.memorise_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByUrl(String url);
    Image findTopByUrlOrderByUpdateDateDesc(String url);
    List<Image> findByFavouriteAndUserAndIsRemove(boolean isFavourite, User user, boolean isRemove);
    List<Image> findByIsRemoveAndUser(boolean status, User user);
    List<Image> findByUserAndLocationContainingOrUserAndNameContaining(User user, String inforLocation, User user1, String inforName);
}
