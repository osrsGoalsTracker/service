package com.osrsGoalTracker.data.internal;

import java.util.List;

import com.google.inject.Inject;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.data.PlayerDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the PlayerDataService interface.
 */
@Slf4j
public class DefaultPlayerDataService implements PlayerDataService {
    private final GoalTrackerDao goalTrackerDao;

    /**
     * Constructs a new DefaultPlayerDataService.
     *
     * @param goalTrackerDao The GoalTrackerDao instance to use for data operations
     */
    @Inject
    public DefaultPlayerDataService(GoalTrackerDao goalTrackerDao) {
        this.goalTrackerDao = goalTrackerDao;
    }

    @Override
    public List<PlayerEntity> getPlayersForUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            log.error("User ID cannot be null or empty");
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String trimmedUserId = userId.trim();
        log.info("Getting players for user {} from DAO", trimmedUserId);
        return goalTrackerDao.getPlayersForUser(trimmedUserId);
    }
}