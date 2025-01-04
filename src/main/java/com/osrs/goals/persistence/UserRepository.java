package com.osrs.goals.persistence;

import com.osrs.goals.persistence.pojo.dao.User;

import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

/**
 * Repository interface for managing user persistence operations.
 */
public interface UserRepository {
    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve
     * @return The user with the specified ID
     * @throws ResourceNotFoundException if the user does not exist
     */
    User getUser(String userId) throws ResourceNotFoundException;
}