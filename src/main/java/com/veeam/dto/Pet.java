package com.veeam.dto;

import com.google.gson.Gson;
import java.util.List;

public class Pet {

    private long id;
    private String name;
    private String status;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;

    // Getter and Setter methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    // Method to convert Pet object to JSON string
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);  // Convert the current object to a JSON string
    }

    // Method to convert JSON string to Pet object
    public static Pet fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Pet.class);  // Convert JSON string to Pet object
    }

    // Inner Category class
    public static class Category {
        private long id;
        private String name;

        // Getter and Setter methods
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Inner Tag class
    public static class Tag {
        private long id;
        private String name;

        // Getter and Setter methods
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
