package com.osrsGoalTracker.domainlogic;

import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.data.pojo.domain.User;

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

    /**
     * Creates a new user with the given email address.
     *
     * @param email The email address for the new user
     * @return The created User object
     */
    User createUser(String email);

    /**
     * Adds a RuneScape player to a user's account.
     *
     * @param userId     The ID of the user to add the player to
     * @param playerName The name of the RuneScape player to add
     * @return The created player entity
     * @throws IllegalArgumentException  if userId or playerName is null or empty
     * @throws ResourceNotFoundException if the user doesn't exist
     */
    PlayerEntity addPlayerToUser(String userId, String playerName);
}
