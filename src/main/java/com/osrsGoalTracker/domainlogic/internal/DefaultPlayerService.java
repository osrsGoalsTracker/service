package com.osrsGoalTracker.domainlogic.internal;

import java.util.List;

import com.google.inject.Inject;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.data.PlayerDataService;
import com.osrsGoalTracker.domainlogic.PlayerService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the PlayerService interface.
 */
@Slf4j
public class DefaultPlayerService implements PlayerService {
    private final PlayerDataService playerDataService;

    /**
     * Constructs a new DefaultPlayerService.
     *
     * @param playerDataService The PlayerDataService instance to use for data
     *                          operations
     */
    @Inject
    public DefaultPlayerService(PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;
    }

    @Override
    public List<PlayerEntity> getPlayersForUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            log.error("User ID cannot be null or empty");
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String trimmedUserId = userId.trim();
        log.info("Getting players for user {}", trimmedUserId);
        return playerDataService.getPlayersForUser(trimmedUserId);
    }
}