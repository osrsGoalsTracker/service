package com.osrsGoalTracker.data;

import com.osrsGoalTracker.data.pojo.domain.User;

import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

/**
 * Service interface for managing user data operations.
 * This service provides methods to interact with user data in the data layer.
 */
public interface UserDataService {
    /**
     * Retrieves a user by their ID.
     *
     * @param userId The unique identifier of the user
     * @return User object containing user data
     * @throws ResourceNotFoundException if user doesn't exist
     */
    User getUser(String userId);

    /**
     * Creates a new user with the given email address.
     *
     * @param email The email address for the new user
     * @return The created User object
     */
    User createUser(String email);
}
