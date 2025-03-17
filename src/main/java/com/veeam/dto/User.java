package com.veeam.dto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    // Getter and Setter methods for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * Convert the User object to a JSON string
     * @return JSON representation of the User object
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);  // Converting User object to JSON string
    }

    /**
     * Convert JSON string to a User object
     * @param json The JSON string to convert
     * @return The User object
     */
    public static User fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);  // Converting JSON string to User object
    }

    /**
     * Convert a list of User objects to JSON array string
     * @param users List of User objects to convert
     * @return JSON string representing the list of users
     */
    public static String toJsonArray(List<User> users) {
        Gson gson = new Gson();
        return gson.toJson(users);
    }

}
