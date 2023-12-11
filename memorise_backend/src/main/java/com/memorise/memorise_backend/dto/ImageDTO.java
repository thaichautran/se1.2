package com.memorise.memorise_backend.dto;

import java.util.Date;

public class ImageDTO {
    private String url;
    private String name;
    private String location;
    private String description;
    private int userId;
    private Date createDate;
    private Date updateDate;

    public ImageDTO() {

    }

    public ImageDTO(String url, String name, String location, String description, int userId) {
        this.url = url;
        this.name = name;
        this.location = location;
        this.description = description;
        this.userId = userId;

    }


    public ImageDTO(String url, String name, String location, String description, int userId, Date createDate, Date updateDate) {
        this.url = url;
        this.name = name;
        this.location = location;
        this.description = description;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
