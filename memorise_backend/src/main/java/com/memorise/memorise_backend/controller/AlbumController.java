package com.memorise.memorise_backend.controller;

import com.memorise.memorise_backend.dto.AlbumDTO;
import com.memorise.memorise_backend.imp.AlbumServiceImp;
import com.memorise.memorise_backend.imp.CloudinaryServiceImp;
import com.memorise.memorise_backend.payload.RespondData;
import com.memorise.memorise_backend.payload.request.UploadRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/albums")
@Tag(name = "Process album")
@SecurityRequirement(name = "bearerAuth")
public class AlbumController {

    @Autowired
    CloudinaryServiceImp cloudinaryServiceImp;

    @Autowired
    AlbumServiceImp albumServiceImp;

    @Operation(
            description = "Create a new album",
            summary = "This API to get create a new album",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping("/create_album")
    public ResponseEntity<?> createFolder(@RequestParam MultipartFile file, @RequestParam String name, @RequestParam String desc){
        RespondData respondData = new RespondData();
        respondData.setDesc("Request is successful!");
        AlbumDTO albumDTO = albumServiceImp.createAlbum(file, name, desc);
        respondData.setData(albumDTO);
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }


    @Operation(
            description = "Add image from home page to album",
            summary = "This API to add image from home page to album",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping("/upload/home")
    public ResponseEntity<?> uploadImageFromHomePage(@RequestParam int albumId, @RequestParam int imageId){
        RespondData respondData = new RespondData();
        respondData.setData(albumServiceImp.addImageToAlbum(albumId, imageId));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Add image from device to album",
            summary = "This API to upload image from device to album",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping(value="/upload/device", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadImageFromDevice(@ModelAttribute UploadRequest uploadRequest, @RequestParam int albumId){
        RespondData respondData = new RespondData();
        respondData.setData(albumServiceImp.uploadImageToAlbum(uploadRequest, albumId));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Get all images from album",
            summary = "This API to get all images from album",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get_images")
    public ResponseEntity<?> getImagesInAlbum(@RequestParam int albumId){
        RespondData respondData = new RespondData();
        respondData.setData(albumServiceImp.getImagesInAlbum(albumId));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Get albums",
            summary = "This API to get all albums",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get_albums")
    public ResponseEntity<?> getAlbums(){
        RespondData respondData = new RespondData();
        respondData.setData(albumServiceImp.getAlbums());
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            description = "Delete a album",
            summary = "This API to delete an album",
            responses = {
                    @ApiResponse(
                            description = "Request is successful!",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/delete_album")
    public ResponseEntity<?> deleteAlbum(@RequestParam int albumId){
        RespondData respondData = new RespondData();
        respondData.setData(albumServiceImp.deleteAlbum(albumId));
        respondData.setDesc("Request is successfully");
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
