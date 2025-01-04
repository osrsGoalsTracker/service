package com.osrs.goals.domainlogic;

import com.osrs.goals.data.pojo.domain.User;

import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

/**
 * Service interface for managing user operations.
 * This service provides methods to interact with user data in the domain layer.
 */
public interface UserService {
    /**
     * Retrieves a user by their ID.
     *
     * @param userId The unique identifier of the user
     * @return User object containing user data
     * @throws ResourceNotFoundException if user doesn't exist
     */
    User getUser(String userId);
}
