package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.dto.ImageDTO;
import com.memorise.memorise_backend.imp.ImageServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@Tag(name = "Process images and videos")
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

    @Operation(
            description = "Update a favourite image",
            summary = "This API to make a favourite image",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/favourite")
    public ResponseEntity<?> updateFavouriteImage(@RequestParam int id, @RequestParam boolean status){
        RespondData respondData = new RespondData();

        ImageDTO imageDTO = imageServiceImp.updateFavouriteImage(id, status);
        respondData.setData(imageDTO);
        respondData.setDesc("Update image favourite status successfully!");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }



    @Operation(
            description = "Get all favourite images",
            summary = "This API to get all favourite images",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get_favourite")
    public ResponseEntity<?> getFavouriteImages(){
        RespondData respondData = new RespondData();

        List<ImageDTO> imageDTOS = imageServiceImp.getFavouriteImages();
        respondData.setData(imageDTOS);
        respondData.setDesc("Get favourite images successfully!");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }


    @Operation(
            description = "Download images and videos",
            summary = "This API to download images and videos",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/download")
    public ResponseEntity<?> downloadImage(@RequestParam String url) {
        RespondData respondData = new RespondData();
        Resource resource = imageServiceImp.downloadImage(url);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @Operation(
            description = "Move to trash bin",
            summary = "This API to move an image to trash bin",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/trash")
    public ResponseEntity<?> moveImageToTrashBin(@RequestParam int id){
        RespondData respondData = new RespondData();
        respondData.setData(imageServiceImp.moveImageToTrashBin(id));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Get images from trash bin",
            summary = "This API get images which is moved trash bin",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get_trash")
    public ResponseEntity<?> getImagesFromTrashBin(){
        RespondData respondData = new RespondData();
        respondData.setData(imageServiceImp.getImagesFromTrashBin());
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Delete an image permanently",
            summary = "This API to delete an image permanently",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/delete_image")
    public ResponseEntity<?> deleteImage(@RequestParam int id){
        RespondData respondData = new RespondData();
        respondData.setData(imageServiceImp.deleteImage(id));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Restore an image from trash bin",
            summary = "This API to restore an image from trash bin to home page",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/restore")
    public ResponseEntity<?> restoreImageFromTrash(@RequestParam int id){
        RespondData respondData = new RespondData();
        respondData.setData(imageServiceImp.restoreImageFromTrash(id));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }


}
