package com.veeam.api;

import com.veeam.dto.Pet;
import com.veeam.utils.ApiResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PetTests {

	private PetApi petApi = new PetApi();

	// Positive Test: Create a pet successfully
	@Test
	public void testCreatePetWithValidData() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Dog");
		pet.setStatus("available");

		ApiResponse response = petApi.createPet(pet);
		assertEquals(200, response.getStatusCode()); // Expecting successful creation (200 OK)
		assertNotNull(response.getBody()); // Ensuring there is a response body
	}

	// Negative Test: Create pet with duplicate ID
	@Test
	public void testCreatePetWithDuplicateId() {
		Pet pet = new Pet();
		pet.setId(1); // Assuming ID 1 already exists
		pet.setName("Cat");
		pet.setStatus("available");

		ApiResponse response = petApi.createPet(pet);
		assertEquals(409, response.getStatusCode()); // Expecting Conflict error (409) due to duplicate ID
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Create pet with missing required fields (e.g., pet id)
	@Test
	public void testCreatePetWithEmptyPetId() {
		Pet pet = new Pet(); // Missing id
		pet.setName("Kitty");
		pet.setStatus("available");

		ApiResponse response = petApi.createPet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to missing required fields
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Create pet with missing required fields (e.g., status)
	@Test
	public void testCreatePetWithEmptyName() {
		Pet pet = new Pet(); // Missing status
		pet.setId(2);
		pet.setStatus("available");

		ApiResponse response = petApi.createPet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to missing required fields
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Create pet with missing required fields (e.g., status)
	@Test
	public void testCreatePetWithEmptyStatus() {
		Pet pet = new Pet(); // Missing status
		pet.setId(3);
		pet.setName("Cat2");

		ApiResponse response = petApi.createPet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to missing required fields
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Create pet with completely empty JSON body
	@Test
	public void testCreatePetWithEmptyBody() {
		Pet pet = new Pet(); // No fields set, empty body

		ApiResponse response = petApi.createPet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to empty body
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Create pet with invalid status
	@Test
	public void testCreatePetWithInvalidStatus() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Invalid Dog");
		pet.setStatus("not-a-status"); // Invalid status

		ApiResponse response = petApi.createPet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to invalid status
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Create pet with extra long filed e.g name
	@Test
	public void testCreatePetWithLongName() {
		Pet pet = new Pet();
		pet.setId(5);
		pet.setName("A".repeat(256)); // Assuming name has a max length of 255 characters
		pet.setStatus("available");

		ApiResponse response = petApi.createPet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to name length being too long
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Positive Test: Update pet using put with valid data
	@Test
	public void testUpdateWithValidData() {
		Pet pet = new Pet();
		pet.setId(1); // Assuming a pet with ID 1 exists
		pet.setName("Updated Dog");
		pet.setStatus("available");

		ApiResponse response = petApi.updatePet(pet);
		assertEquals(200, response.getStatusCode()); // Expecting a successful update (200 OK)
		assertNotNull(response.getBody()); // Ensuring there is a response body
	}

	// Negative Test: Update pet using put with invalid ID
	@Test
	public void testUpdatePetWithInvalidId() {
		Pet pet = new Pet();
		pet.setId(-1); // Invalid ID
		pet.setName("Updated Dog");
		pet.setStatus("available");

		ApiResponse response = petApi.updatePet(pet);
		assertEquals(404, response.getStatusCode()); // Expecting 404 Not Found, as the ID is invalid
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Update pet using put with invalid data (invalid JSON or
	// missing fields)
	@Test
	public void testUpdatePetWithInvalidData() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName(""); // Invalid empty name field
		pet.setStatus("invalid-status"); // Invalid status

		ApiResponse response = petApi.updatePet(pet);
		assertEquals(405, response.getStatusCode()); // Expecting 405 for invalid input
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Update pet with missing required fields (name or status)
	@Test
	public void testUpdatePetWithMissingRequiredFields() {
		Pet pet = new Pet();
		pet.setId(1); // Assuming pet ID 1 exists
		pet.setName(""); // Missing name
		pet.setStatus(""); // Missing status

		ApiResponse response = petApi.updatePet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to missing required fields
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Update pet with completely empty body (no fields set)
	@Test
	public void testUpdatePetWithEmptyBody() {
		Pet pet = new Pet(); // No fields set, empty body

		ApiResponse response = petApi.updatePet(pet);
		assertEquals(400, response.getStatusCode()); // Expecting error due to empty body
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Positive Test: Update pet using POST with valid data
	@Test
	public void testUpdatePetUsingPostWithValidData() {
		Pet pet = new Pet();
		pet.setId(1); // Assuming pet with ID 1 exists
		pet.setName("Updated Dog");
		pet.setStatus("sold");

		ApiResponse response = petApi.updatePetUsingPost(pet);
		assertEquals(200, response.getStatusCode()); // Expecting successful update (200 OK)
		assertNotNull(response.getBody()); // Ensuring there is a response body
	}

	// Negative Test: Update pet using POST with invalid ID
	@Test
	public void testUpdatePetUsingPostWithInvalidId() {
		Pet pet = new Pet();
		pet.setId(-1); // Invalid ID
		pet.setName("Updated Dog");
		pet.setStatus("available");

		ApiResponse response = petApi.updatePetUsingPost(pet);
		assertEquals(404, response.getStatusCode()); // Expecting 404 Not Found, as the ID is invalid
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Update pet using POST with invalid data (invalid JSON or
	// missing required fields)
	@Test
	public void testUpdatePetUsingPostWithInvalidData() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName(""); // Invalid empty name field
		pet.setStatus("invalid-status"); // Invalid status

		ApiResponse response = petApi.updatePetUsingPost(pet);
		assertEquals(405, response.getStatusCode()); // Expecting 405 for invalid input
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Test Get Pets by Status with valid status
	@Test
	public void testGetPetsByValidStatus() {
		ApiResponse response = petApi.getPetsByStatus("available");
		assertEquals(200, response.getStatusCode()); // Expecting successful retrieval
		assertNotNull(response.getBody()); // Ensuring there is a response body
	}

	// Negative Test: Get Pets by Invalid Status
	@Test
	public void testGetPetsByInvalidStatus() {
		ApiResponse response = petApi.getPetsByStatus("invalid-status");
		assertEquals(400, response.getStatusCode()); // Expecting error for invalid status
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Get pets by missing status
	@Test
	public void testGetPetsByMissingStatus() {
		ApiResponse response = petApi.getPetsByStatus(""); // Empty status
		assertEquals(400, response.getStatusCode()); // Expecting error due to missing status
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Positive Test: Get pets by multiple valid statuses
	@Test
	public void testGetPetsByMultipleStatuses() {
		// Using multiple statuses: available, pending
		ApiResponse response = petApi.getPetsByStatus("available,pending");
		assertEquals(200, response.getStatusCode()); // Expecting successful retrieval
		assertNotNull(response.getBody()); // Ensuring there is a response body

		// Optionally, check if the response contains pets with these statuses
		assertTrue(response.getBody().contains("available"));
		assertTrue(response.getBody().contains("pending"));
	}

	// Negative Test: Get pets by invalid statuses
	@Test
	public void testGetPetsByInvalidMultipleStatuses() {
		// Using an invalid status value and a valid one
		ApiResponse response = petApi.getPetsByStatus("not-a-status,available");
		assertEquals(400, response.getStatusCode()); // Expecting error due to invalid status
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Positive Test: Get pet by ID successfully
	@Test
	public void testGetPetByValidId() {
		Pet pet = petApi.getPetById(1);
		assertNotNull(pet); // Expecting a pet object to be returned
		assertEquals(1, pet.getId()); // Expecting the pet ID to be 1
	}

	// Negative Test: Test petId that does not exist
	@Test
	public void testGetPetWithNonExistentId() {
		long invalidPetId = 9999; // A non-existent pet ID
		Pet pet = petApi.getPetById(invalidPetId);
		assertNull(pet); // Expecting null since the pet does not exist
	}

	// Negative Test: Test petId with negative value
	@Test
	public void testGetPetWithNegativeId() {
		long invalidPetId = -1; // Negative ID is not valid
		Pet pet = petApi.getPetById(invalidPetId);
		assertNull(pet); // Expecting null or appropriate error response
	}

	// Positive Test: Delete pet with valid ID and API key
	@Test
	public void testDeletePetWithValidId() {
		long petId = 1; // Assuming a pet with ID 1 exists
		ApiResponse response = petApi.deletePet(petId, "special-key"); // Pass the API key in the request
		assertEquals(200, response.getStatusCode()); // Expecting successful deletion (200 OK)
		assertNotNull(response.getBody()); // Ensuring there is a response body
	}

	// Negative Test: Try to delete pet with invalid ID and API key
	@Test
	public void testDeletePetWithInvalidId() {
		long invalidPetId = 9999; // Non-existent pet ID
		ApiResponse response = petApi.deletePet(invalidPetId, "special-key");
		assertEquals(404, response.getStatusCode()); // Expecting Not Found (404) because the pet does not exist
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Try to delete pet without API key
	@Test
	public void testDeletePetWithoutApiKey() {
		long petId = 1;
		ApiResponse response = petApi.deletePet(petId, null); // No API key provided
		assertEquals(401, response.getStatusCode()); // Expecting Unauthorized (401) due to missing API key
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Test Image Upload with valid petId
	@Test
	public void testUploadImageWithValidData() {
		ApiResponse response = petApi.uploadImage(1, "image.jpg", "Image of my pet");
		assertEquals(200, response.getStatusCode());
		assertNotNull(response.getBody()); // Ensuring there is a response body
	}

	// Negative Test: Upload image with invalid petId
	@Test
	public void testUploadImageWithInvalidPetId() {
		ApiResponse response = petApi.uploadImage(9999, "image.jpg", "Image of my pet");
		assertEquals(404, response.getStatusCode()); // Expecting 404 because pet with ID 9999 does not exist
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Negative Test: Upload image with missing file
	@Test
	public void testUploadImageWithMissingFile() {
		ApiResponse response = petApi.uploadImage(1, null, "Image of my pet");
		assertEquals(400, response.getStatusCode()); // Expecting 400 due to missing file
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}

	// Test Image Upload with unsupported file type (415)
	@Test
	public void testUploadImageWithUnsupportedFile() {
		ApiResponse response = petApi.uploadImage(1, "invalidfile.txt", "Image of my pet");
		assertEquals(415, response.getStatusCode()); // Expecting 415 for unsupported file type
		assertTrue(response.getBody().contains("error")); // Ensure response body contains error message
	}
}
