package com.memorise.memorise_backend.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;

@Embeddable
public class KeyImageAlbum implements Serializable {
    @Column(name = "album_id")
    private int albumId;

    @Column(name = "image_id")
    private int imageId;

    public KeyImageAlbum() {

    }

    public KeyImageAlbum(int albumId, int imageId) {
        this.albumId = albumId;
        this.imageId = imageId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
