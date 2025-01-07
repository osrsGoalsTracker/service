package com.osrsGoalTracker.data;

import java.util.List;

import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;

/**
 * Service interface for managing player data operations.
 */
public interface PlayerDataService {
    /**
     * Retrieves all players associated with a user.
     *
     * @param userId The ID of the user
     * @return A list of players associated with the user
     * @throws IllegalArgumentException if userId is null or empty
     */
    List<PlayerEntity> getPlayersForUser(String userId);
}