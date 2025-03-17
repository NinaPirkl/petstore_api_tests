
package com.veeam.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import com.veeam.dto.Store;
import com.veeam.utils.ApiResponse;

public class StoreApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/store";  // Base URL for Store API
    private final HttpClient client;
    private static final Logger logger = Logger.getLogger(StoreApi.class.getName());

    public StoreApi() {
        this.client = HttpClient.newHttpClient();
    }
    
    public ApiResponse placeOrder(Store order) {
        String url = BASE_URL + "/order";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(order.toJson()))  // Convert Order to JSON
                .build();

        return executeRequest(request);
    }
    
    public ApiResponse getOrderById(long orderId) {
        String url = BASE_URL + "/order/" + orderId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return executeRequest(request);
    }
    
    public ApiResponse deleteOrder(long orderId) {
        String url = BASE_URL + "/order/" + orderId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        return executeRequest(request);
    }
    
    public ApiResponse getInventory() {
        String url = BASE_URL + "/inventory";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return executeRequest(request);
    }
    
    private ApiResponse executeRequest(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());
        } catch (IOException | InterruptedException e) {
            logger.severe("Error executing request: " + e.getMessage());
            return new ApiResponse(500, "Internal server error");
        }
    }
}
