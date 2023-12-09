package com.memorise.memorise_backend.dto;

public class ImageDTO {
    private String url;
    private String name;
    private String location;
    private String description;
    private int userId;

    public ImageDTO() {

    }

    public ImageDTO(String url, String name, String location, String description, int userId) {
        this.url = url;
        this.name = name;
        this.location = location;
        this.description = description;
        this.userId = userId;
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
}
