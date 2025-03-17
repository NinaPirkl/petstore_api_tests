package com.veeam.api;

import com.veeam.utils.ApiResponse;
import com.veeam.dto.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTests {

    private UserApi userApi = new UserApi();

    // Positive Test: Create a user successfully
    @Test
    public void testCreateUserWithValidData() {
        User user = new User();
        user.setId(001);
        user.setUsername("johnDoe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setUserStatus(1);

        ApiResponse response = userApi.createUser(user);
        assertEquals(200, response.getStatusCode());  // Expecting successful creation (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }

    // Negative Test: Create user with invalid data (e.g., empty fields)
    @Test
    public void testCreateUserWithInvalidData() {
        User user = new User();
        user.setUsername("_>±");  // Invalid username
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("invalidmail");  // Invalid email
        user.setPassword("test1");
        user.setPhone("1234567890");
        user.setUserStatus(1);

        ApiResponse response = userApi.createUser(user);
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to invalid data
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Create user with duplicate ID
    @Test
    public void testCreateUserWithDuplicateId() {
        User user = new User();
        user.setId(001);  // Assuming ID 001 already exists
        user.setUsername("JohnyD");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setUserStatus(1);

        ApiResponse response = userApi.createUser(user);
        assertEquals(409, response.getStatusCode());  // Expecting Conflict error (409) due to duplicate ID
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }
    
    // Negative Test: Create user with duplicate username
    @Test
    public void testCreateUserWithDuplicateUsername() {
        User user = new User();
        user.setUsername("johnDoe");  // Assuming "johnDoe" already exists
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password1");
        user.setPhone("1234567890");
        user.setUserStatus(1);

        ApiResponse response = userApi.createUser(user);
        assertEquals(409, response.getStatusCode());  // Expecting 409 Conflict due to duplicate username
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Create user with missing username
    @Test
    public void testCreateUserWithMissingUsername() {
        User user = new User();
        user.setUsername("");  // Missing username
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setUserStatus(1);

        ApiResponse response = userApi.createUser(user);
        assertEquals(400, response.getStatusCode());  // Expecting 400 due to missing required fields
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Create user with missing password
    @Test
    public void testCreateUserWithMissingPassword() {
        User user = new User();
        user.setUsername("johnDoe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("");  // Missing password
        user.setPhone("123-456-7890");
        user.setUserStatus(1);

        ApiResponse response = userApi.createUser(user);
        assertEquals(400, response.getStatusCode());  // Expecting 400 due to missing required fields
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }
    
    // Negative Test: Create user with empty body (missing required fields)
    @Test
    public void testCreateUserWithEmptyBody() {
        User user = new User(); // Empty user object, no fields set

        ApiResponse response = userApi.createUser(user);  // Trying to create user with no data
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to missing required fields
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Positive Test: Create multiple users with valid data (createWithArray)
    @Test
    public void testCreateUsersWithArrayValidData() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe1@example.com");
        user1.setPassword("password123");
        user1.setPhone("123-456-7890");
        user1.setUserStatus(1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password456");
        user2.setPhone("987-654-3210");
        user2.setUserStatus(1);

        List<User> users = Arrays.asList(user1, user2);

        ApiResponse response = userApi.createUsersWithArray(users); // Assuming this method exists for creating multiple users
        assertEquals(200, response.getStatusCode());  // Expecting successful creation (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }
    
 // Negative Test: Create multiple users with invalid data (createWithArray)
    @Test
    public void testCreateUsersWithArrayWithInvalidData() {
        User user1 = new User();
        user1.setUsername("");  // Empty username
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("invalidmail");  // Invalid email
        user1.setPassword("password123");
        user1.setPhone("1234567890");
        user1.setUserStatus(1);

        User user2 = new User();
        user2.setUsername("validUsername");
        user2.setFirstName("John2");
        user2.setLastName("Doe2");
        user2.setEmail("validEmail@example.com");
        user2.setPassword("password123");
        user2.setPhone("123-456-7891");
        user2.setUserStatus(1);

        // Replace User[] with List<User>
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);  // One invalid, one valid

        ApiResponse response = userApi.createUsersWithArray(users);  // Now passing the List<User> instead of User[]
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to invalid user1
        assertTrue(response.getBody().contains("error"));  // Ensure error message is in the response body
    }

    // Negative Test: Create multiple users with an empty list
    @Test
    public void testCreateUsersWithArrayEmptyList() {
        List<User> users = new ArrayList<>();  // Empty list

        ApiResponse response = userApi.createUsersWithList(users);
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request (since list is empty)
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Positive Test: Create multiple users with valid data (createWithList)
    @Test
    public void testCreateUsersWithListValidData() {
        User user1 = new User();
        user1.setUsername("user3");
        user1.setFirstName("Alice");
        user1.setLastName("Johnson");
        user1.setEmail("alice.johnson@example.com");
        user1.setPassword("password789");
        user1.setPhone("555-123-4567");
        user1.setUserStatus(1);

        User user2 = new User();
        user2.setUsername("user4");
        user2.setFirstName("Bob");
        user2.setLastName("Brown");
        user2.setEmail("bob.brown@example.com");
        user2.setPassword("password000");
        user2.setPhone("555-987-6543");
        user2.setUserStatus(1);

        List<User> users = Arrays.asList(user1, user2);

        ApiResponse response = userApi.createUsersWithList(users); // Assuming this method exists for creating multiple users
        assertEquals(200, response.getStatusCode());  // Expecting successful creation (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }
    
    // Negative Test: Create multiple users with empty or invalid fields
    @Test
    public void testCreateUsersWithListWithInvalidData() {
        User user1 = new User();
        user1.setUsername("");  // Empty username
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("invalidmail");  // Invalid email
        user1.setPassword("password123");
        user1.setPhone("1234567890");
        user1.setUserStatus(1);

        User user2 = new User();
        user2.setUsername("validUsername");
        user2.setFirstName("John2");
        user2.setLastName("Doe2");
        user2.setEmail("validEmail@example.com");
        user2.setPassword("password123");
        user2.setPhone("123-456-7891");
        user2.setUserStatus(1);

        List<User> users = Arrays.asList(user1, user2);  // One invalid, one valid

        ApiResponse response = userApi.createUsersWithList(users);
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to invalid user1
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Create multiple users with an empty list
    @Test
    public void testCreateUsersWithListEmptyList() {
        List<User> users = new ArrayList<>();  // Empty list

        ApiResponse response = userApi.createUsersWithList(users);
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request (since list is empty)
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Positive Test: Get user by username successfully
    @Test
    public void testGetUserByUsername() {
        ApiResponse response = userApi.getUserByUsername("johnDoe");  // Get the API response
        assertEquals(200, response.getStatusCode());  // Check for 200 OK response
        assertNotNull(response.getBody());  // Ensure the response body is not null
        User user = User.fromJson(response.getBody());  // Convert the response body (JSON) to a User object
        assertEquals("johnDoe", user.getUsername());  // Expect the username to be "johnDoe"
    }


    // Negative Test: Get user with non-existing username
    @Test
    public void testGetUserWithInvalidUsername() {
        ApiResponse response = userApi.getUserByUsername("invalidUser");  // Get the API response
        assertEquals(404, response.getStatusCode());  // Expecting 404 Not Found since the user doesn't exist
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Positive Test: Update user with valid data
    @Test
    public void testUpdateUserWithValidData() {
        User user = new User();
        user.setUsername("johnDoe");
        user.setFirstName("John Updated");
        user.setLastName("Doe Updated");
        user.setEmail("updatedjohn@example.com");
        user.setPassword("newpassword123");
        user.setPhone("987-654-3210");
        user.setUserStatus(1);

        ApiResponse response = userApi.updateUser("johnDoe", user);
        assertEquals(200, response.getStatusCode());  // Expecting successful update (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }

    // Negative Test: Update user with invalid username
    @Test
    public void testUpdateUserWithInvalidUsername() {
        User user = new User();
        user.setUsername("*/|,#");
        user.setFirstName("Invalid");
        user.setLastName("User");
        user.setEmail("valid@example.com");
        user.setPassword("invalid123");
        user.setPhone("000-000-0000");
        user.setUserStatus(0);

        ApiResponse response = userApi.updateUser("*/|,#", user);
        assertEquals(404, response.getStatusCode());  // Expecting 404 Not Found since the user doesn't exist
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Update user with missing username
    @Test
    public void testUpdateUserWithEmptyUsername() {
        User user = new User();
        user.setUsername("");  // Missing username
        user.setFirstName("Updated John");
        user.setLastName("Updated Doe");
        user.setEmail("updatedjohndoe@example.com");
        user.setPassword("newpassword123");
        user.setPhone("987-654-3210");
        user.setUserStatus(1);

        ApiResponse response = userApi.updateUser("", user);  // Trying to update with empty username
        assertEquals(400, response.getStatusCode());  // Expecting 400 due to missing required fields
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Update user with invalid data (invalid email)
    @Test
    public void testUpdateUserWithInvalidData() {
        User user = new User();
        user.setUsername("johnDoe");  // Valid username
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("*/|,#");  // Invalid email format
        user.setPassword("password123");
        user.setPhone("1234567890");
        user.setUserStatus(1);

        ApiResponse response = userApi.updateUser("johnDoe", user);
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to invalid email format
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Negative Test: Update user with missing body (empty body)
    @Test
    public void testUpdateUserWithEmptyBody() {
        User user = new User(); // Passing an empty user object
        ApiResponse response = userApi.updateUser("johnDoe", user);  // Trying to update user with empty body
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to missing body data
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Positive Test: Delete user with valid username
    @Test
    public void testDeleteUserWithValidUsername() {
        ApiResponse response = userApi.deleteUser("johnDoe");
        assertEquals(200, response.getStatusCode());  // Expecting successful deletion (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }

    // Negative Test: Delete user with invalid username
    @Test
    public void testDeleteUserWithInvalidUsername() {
        ApiResponse response = userApi.deleteUser("ubfdbdfser1");  // Non-existing user
        assertEquals(404, response.getStatusCode());  // Expecting 404 Not Found since the user doesn't exist
    }

    // Negative Test: Delete user with missing username
    @Test
    public void testDeleteUserWithMissingUsername() {
        ApiResponse response = userApi.deleteUser("");  // Missing username
        assertEquals(400, response.getStatusCode());  // Expecting 400 due to missing required fields
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Test Login with valid credentials
    @Test
    public void testLoginWithValidCredentials() {
        ApiResponse response = userApi.loginUser("test", "abc123");
        assertEquals(200, response.getStatusCode());  // Expecting successful login (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }

    // Negative Test: Login with invalid username
    @Test
    public void testLoginWithInvalidUsername() {
        ApiResponse response = userApi.loginUser("invalidUsername", "abc123");
        assertEquals(404, response.getStatusCode());  // Expecting 404 for user not found
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }
 
    // Negative Test: Login with correct username but incorrect password
    @Test
    public void testLoginWithIncorrectPassword() {
        ApiResponse response = userApi.loginUser("test", "wrongPassword");
        assertEquals(401, response.getStatusCode());  // Expecting 401 for incorrect password
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }
    
    // Negative Test: Login with empty username and password
    @Test
    public void testLoginWithEmptyFields() {
        ApiResponse response = userApi.loginUser("", "");  // Passing empty username and password
        assertEquals(400, response.getStatusCode());  // Expecting 400 Bad Request due to missing required fields
        assertTrue(response.getBody().contains("error"));  // Ensure response body contains error message
    }

    // Test Logout functionality
    @Test
    public void testLogout() {
        ApiResponse response = userApi.logoutUser();
        assertEquals(200, response.getStatusCode());  // Expecting successful logout (200 OK)
        assertNotNull(response.getBody());  // Ensuring there is a response body
    }
}
