package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.imp.ImageServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@Tag(name = "Get images and videos")
@SecurityRequirement(name = "bearerAuth")
public class ImageController {

    @Autowired
    ImageServiceImp imageServiceImp;

    @Operation(
            description = "Get images and videos",
            summary = "This API to get all images and videos of an authenticated user",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get_all")
    public ResponseEntity<?> getAllImages(){
        RespondData respondData = new RespondData();
        List<ImageDTO> imageDTOList = imageServiceImp.getAllImages();
        if(imageDTOList != null){
            respondData.setDesc("Get files successfully!");
            respondData.setData(imageDTOList);
        } else {
            respondData.setDesc("User does not have some files");
            respondData.setData(null);
        }

        return  new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PutMapping("/favourite")
    public ResponseEntity<?> updateFavouriteImage(@RequestParam int id){
        RespondData respondData = new RespondData();

        ImageDTO imageDTO = imageServiceImp.updateFavouriteImage(id);
        respondData.setData(imageDTO);
        respondData.setDesc("Update image favourite successfully!");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @GetMapping("/get_favourite")
    public ResponseEntity<?> getFavouriteImages(){
        RespondData respondData = new RespondData();

        List<ImageDTO> imageDTOS = imageServiceImp.getFavouriteImages();
        respondData.setData(imageDTOS);
        respondData.setDesc("Get favourite images successfully!");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
