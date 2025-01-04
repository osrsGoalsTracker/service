package com.osrs.goals.data;

import com.osrs.goals.data.pojo.domain.User;

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
}
