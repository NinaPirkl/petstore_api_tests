
package com.veeam.api;

import com.veeam.dto.Store;
import com.veeam.utils.ApiResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class StoreTests {

    private StoreApi storeApi = new StoreApi();
    
    // Positive Test: Place a new order
    @Test
    public void testPlaceOrder() {
        Store order = new Store();
        order.setPetId(1);
        order.setQuantity(2);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("placed");
        order.setComplete(true);
        
        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    
    // Negative Test: Place an order with invalid petId (negative value)
    @Test
    public void testPlaceOrderWithInvalidPetId() {
        Store order = new Store();
        order.setPetId(-1);  // Invalid petId
        order.setQuantity(2);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("placed");
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting error due to invalid petId
        assertTrue(response.getBody().contains("Invalid pet ID supplied"));  // Ensure response body contains an error message
    }

    // Negative Test: Place an order with duplicate orderId
    @Test
    public void testPlaceOrderWithDuplicateId() {
        Store order = new Store();
        order.setId(1);  // Assume order with ID 1 already exists
        order.setPetId(1);
        order.setQuantity(2);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("placed");
        order.setComplete(true);

        // Call API to create the order
        ApiResponse response = storeApi.placeOrder(order);
        
        // Expecting 409 Conflict error for duplicate order ID
        assertEquals(409, response.getStatusCode());  
        assertTrue(response.getBody().contains("error"));  // Ensure the response body contains an error message
    }

    // Negative Test: Place an order with missing petId
    @Test
    public void testPlaceOrderWithMissingPetId() {
        Store order = new Store();
        order.setQuantity(2);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("placed");
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting error due to missing petId
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains an error message
    }
    
    // Negative Test: Place an order with invalid status
    @Test
    public void testPlaceOrderWithInvalidStatus() {
        Store order = new Store();
        order.setPetId(1);
        order.setQuantity(2);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("invalid-status");  // Invalid status
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting a 400 error for invalid status
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Place an order with missing status
    @Test
    public void testPlaceOrderWithMissingStatus() {
        Store order = new Store();
        order.setPetId(1);
        order.setQuantity(2);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting error due to missing status
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains an error message
    }
    
    // Negative Test: Place an order with negative quantity
    @Test
    public void testPlaceOrderWithNegativeQuantity() {
        Store order = new Store();
        order.setPetId(1);
        order.setQuantity(-1);  // Negative quantity
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("placed");
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting a 400 error for invalid quantity
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Place an order with missing quantity
    @Test
    public void testPlaceOrderWithMissingQuantity() {
        Store order = new Store();
        order.setPetId(1);
        order.setShipDate("2024-12-31T20:35:51.374Z");  
        order.setStatus("placed");
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting error due to missing quantity
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains an error message
    }

    // Negative Test: Place an order with invalid shipDate format
    @Test
    public void testPlaceOrderWithInvalidShipDate() {
        Store order = new Store();
        order.setPetId(1);
        order.setQuantity(2);
        order.setShipDate("invalid-date");  // Invalid date format
        order.setStatus("placed");
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting error due to invalid date format
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains an error message
    }

    // Negative Test: Place an order with future shipDate
    @Test
    public void testPlaceOrderWithFutureShipDate() {
        Store order = new Store();
        order.setPetId(1);
        order.setQuantity(2);
        order.setShipDate("2025-12-31T20:35:51.374Z"); // Future date
        order.setStatus("placed");
        order.setComplete(true);

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(200, response.getStatusCode()); // Assuming API handles future dates without error
        assertNotNull(response.getBody()); // Ensure response body is not null
    }

    // Negative Test: Place an order with an empty body
    @Test
    public void testPlaceOrderWithEmptyBody() {
        Store order = new Store();  // Empty body

        ApiResponse response = storeApi.placeOrder(order);
        assertEquals(400, response.getStatusCode());  // Expecting error due to empty body
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains an error message
    }

    // Positive Test: Get an order by ID
    @Test
    public void testGetOrderById() {
        long orderId = 1;  // Assume we have a valid order ID
        ApiResponse response = storeApi.getOrderById(orderId);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("id"));
        assertTrue(response.getBody().contains("petId"));
    }
    
    // Negative Test: Get order by non-existent ID
    @Test
    public void testGetOrderNotFound() {
        long orderId = 9999;  // Non-existent order ID
        
        ApiResponse response = storeApi.getOrderById(orderId);
        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().contains("Order not found"));
    }

    // Negative Test: Get an order by invalid ID (negative value)
    @Test
    public void testGetOrderByInvalidId() {
        long invalidOrderId = -1;  // Invalid ID (negative value)
        ApiResponse response = storeApi.getOrderById(invalidOrderId);
        assertEquals(400, response.getStatusCode());  // Expecting error due to invalid ID
        assertTrue(response.getBody().contains("Invalid ID supplied"));
    }

    // Positive Test: Delete an order by ID
    @Test
    public void testDeleteOrder() {
        long orderId = 1;  // Assume we have a valid order ID to delete
        ApiResponse response = storeApi.deleteOrder(orderId);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    
    // Negative Test: Delete an order by invalid ID (negative value)
    @Test
    public void testDeleteOrderByInvalidId() {
        long invalidOrderId = -1;  // Invalid ID (negative value)
        ApiResponse response = storeApi.deleteOrder(invalidOrderId);
        assertEquals(400, response.getStatusCode());  // Expecting error due to invalid ID
        assertTrue(response.getBody().contains("Invalid ID supplied")); 
    }
    
    // Negative Test: Delete an order by non-existent ID
    @Test
    public void testDeleteOrderNotFound() {
        long nonExistentOrderId = 9999;  // Non-existent order ID
        ApiResponse response = storeApi.deleteOrder(nonExistentOrderId);
        assertEquals(404, response.getStatusCode());  // Expecting error for non-existent order
        assertTrue(response.getBody().contains("Order not found"));  
    }
    
    // Positive Test: Get inventory
    @Test
    public void testGetInventory() {
        ApiResponse response = storeApi.getInventory();
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
