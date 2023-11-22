package com.memorise.memorise_backend.entity;

import com.memorise.memorise_backend.entity.key.KeyImageAlbum;
import jakarta.persistence.*;

@Entity(name = "image_album")
public class ImageAlbum {

    @EmbeddedId
    KeyImageAlbum key;

    @ManyToOne
    @JoinColumn(name = "album_id", insertable = false, updatable = false)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private Image image;

    public KeyImageAlbum getKey() {
        return key;
    }

    public void setKey(KeyImageAlbum key) {
        this.key = key;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
