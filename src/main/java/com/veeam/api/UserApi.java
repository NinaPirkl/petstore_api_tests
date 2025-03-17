package com.veeam.api;

import com.veeam.dto.User;
import com.veeam.utils.ApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/user";
    private HttpClient client;

    public UserApi() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Create a new user
     * @param user The user to create
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse createUser(User user) {
        String url = BASE_URL;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(user.toJson()))
            .build();

        return executeRequest(request);
    }

    /**
     * Create multiple users using an array of user objects
     * @param users Array of User objects to be created
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse createUsersWithArray(List<User> users) {
        String url = BASE_URL + "/createWithArray";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(User.toJsonArray(users))) // Convert List to JSON array
            .build();

        return executeRequest(request);
    }

    /**
     * Create multiple users using a list of user objects
     * @param users List of User objects to be created
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse createUsersWithList(List<User> users) {
        String url = BASE_URL + "/createWithList";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(User.toJsonArray(users)))
            .build();

        return executeRequest(request);
    }

    /**
     * Get a user by username
     * @param username The username of the user to retrieve
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse getUserByUsername(String username) {
        String url = BASE_URL + "/user/" + username;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        
        // Execute the request and return the response
        return executeRequest(request);
    }

    /**
     * Update an existing user's data by username
     * @param username The username of the user to update
     * @param user The updated user data
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse updateUser(String username, User user) {
        String url = BASE_URL + "/" + username;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(user.toJson()))
            .build();

        return executeRequest(request);
    }

    /**
     * Delete a user by username
     * @param username The username of the user to delete
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse deleteUser(String username) {
        String url = BASE_URL + "/" + username;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .DELETE()
            .build();

        return executeRequest(request);
    }

    /**
     * Log in a user with the provided username and password
     * @param username The username of the user logging in
     * @param password The password of the user logging in
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse loginUser(String username, String password) {
        String url = BASE_URL + "/login?username=" + username + "&password=" + password;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        return executeRequest(request);
    }

    /**
     * Log out the currently logged-in user
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse logoutUser() {
        String url = BASE_URL + "/logout";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        return executeRequest(request);
    }

    /**
     * Execute the HTTP request and return the response
     * @param request The HTTP request to execute
     * @return ApiResponse object containing status code and response body
     */
    private ApiResponse executeRequest(HttpRequest request) {
        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Return the ApiResponse object for convenience
            return new ApiResponse(response.statusCode(), response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "Internal Server Error");
        }
    }
}
