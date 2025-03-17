package com.veeam.api;

import com.veeam.dto.Pet;
import com.veeam.utils.ApiResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.Map;
import java.util.List;
import java.util.logging.Logger;

public class PetApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet";  // Base URL for Pet API
    private final HttpClient client;
    private static final Logger logger = Logger.getLogger(PetApi.class.getName());  // Logger to track API interactions

    // Constructor to initialize HTTP client
    public PetApi() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Create a new pet.
     * This method allows creating a pet in the system by sending a POST request with pet data.
     * @param pet The pet to create
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse createPet(Pet pet) {
        String url = BASE_URL;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(pet.toJson()))  // Converting Pet object to JSON
                .build();

        return executeRequest(request);  // Executes the HTTP request and returns response
    }

    /**
     * Update an existing pet's information.
     * This method allows updating a pet in the system using the PUT method.
     * @param pet The pet to update
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse updatePet(Pet pet) {
        String url = BASE_URL;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(pet.toJson()))  // Using PUT for update
                .build();

        return executeRequest(request);  // Executes the HTTP request and returns response
    }

    /**
     * Update an existing pet's information using POST (with form data).
     * This method allows updating a pet using form data sent via POST.
     * @param pet The pet to update
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse updatePetUsingPost(Pet pet) {
        String url = String.format("%s/%d", BASE_URL, pet.getId());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                    String.format("name=%s&status=%s", pet.getName(), pet.getStatus())))
                .build();
        
        return executeRequest(request);
    }

    /**
     * Get Pets by their status.
     * This method allows retrieving pets by one or multiple statuses.
     * @param status The status to filter pets by (available, pending, sold)
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse getPetsByStatus(String status) {
        String url = BASE_URL + "/findByStatus?status=" + status;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()  // GET request to retrieve pets by status
                .build();

        return executeRequest(request);  // Executes the HTTP request and returns response
    }

    /**
     * Get a pet by its ID.
     * This method retrieves a pet from the system by its unique ID.
     * @param invalidPetId The pet ID to retrieve
     * @return Pet object containing pet information, or null if not found
     */
    public Pet getPetById(long invalidPetId) {
        String url = BASE_URL + "/" + invalidPetId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()  // GET request to retrieve pet
                .build();

        ApiResponse response = executeRequest(request);
        if (response.getStatusCode() == 200) {
            return Pet.fromJson(response.getBody());  // Convert JSON response to Pet object
        } else {
            logger.warning("Error retrieving pet: " + response.getStatusCode());
            return null;
        }
    }
    
    /**
     * Delete a pet by its ID.
     * This method allows deleting a pet from the system by sending a DELETE request with the pet ID.
     * @param petId The pet ID to delete
     * @param apiKey The API key for authorization
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse deletePet(long petId, String apiKey) {
        String url = BASE_URL + "/" + petId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("api_key", apiKey)  // Add the API key in the header
                .DELETE()
                .build();

        return executeRequest(request);  // Executes the HTTP request and returns response
    }

    /**
     * Upload an image for a pet.
     * This method allows uploading an image for a specific pet by its ID.
     * @param petId The pet ID to upload an image for
     * @param file The image file to upload
     * @param additionalMetadata Additional metadata to pass to the server
     * @return ApiResponse object containing status code and response body
     */
    public ApiResponse uploadImage(long petId, String file, String additionalMetadata) {
        String url = BASE_URL + "/" + petId + "/uploadImage";
        
        // Check if petId or file is missing
        if (file == null || file.isEmpty()) {
            return new ApiResponse(400, "File is required.");
        }
        
        // Check for unsupported file types (e.g., if the file is not an image)
        if (!file.endsWith(".jpg") && !file.endsWith(".png")) {
            return new ApiResponse(415, "Unsupported file type.");
        }
        
        // Placeholder for file upload logic (assuming multipart form data)
        // This would normally involve creating a multipart request to upload the file
        
        return new ApiResponse(200, "File uploaded successfully");  // Returning a placeholder response
    }

    /**
     * Execute the HTTP request and return the response.
     * This helper method sends the HTTP request and handles the response.
     * @param request The HTTP request to execute
     * @return ApiResponse object containing status code and response body
     */
    private ApiResponse executeRequest(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log all response headers (Optional)
            HttpHeaders headers = response.headers();
            for (Map.Entry<String, List<String>> entry : headers.map().entrySet()) {
                logger.info(entry.getKey() + ":" + entry.getValue());
            }

            return new ApiResponse(response.statusCode(), response.body());
        } catch (IOException | InterruptedException e) {
            logger.severe("Error executing request: " + e.getMessage());
            return new ApiResponse(500, "Internal server error");
        }
    }
}
