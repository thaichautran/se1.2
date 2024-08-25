package com.memorise.memorise_backend.dto;

import java.util.Date;
import java.util.List;

public class ImageDTO {
    private int id;
    private String url;
    private String name;
    private String location;
    private String description;
    private Date createDate;
    private Date updateDate;

    private boolean isFavourite = false;
    private boolean isPublic = false;
    private boolean isRemove = false;
    List<Integer> albumIds = null;

    public ImageDTO() {

    }

    public ImageDTO(int id, String url, String name, String location, String description, Date createDate, Date updateDate) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.location = location;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public ImageDTO(int id, String url, String name, String location, String description, Date createDate, Date updateDate, boolean isFavourite, boolean isPublic, boolean isRemove) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.location = location;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isFavourite = isFavourite;
        this.isPublic = isPublic;
        this.isRemove = isRemove;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public List<Integer> getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(List<Integer> albumIds) {
        this.albumIds = albumIds;
    }
}
