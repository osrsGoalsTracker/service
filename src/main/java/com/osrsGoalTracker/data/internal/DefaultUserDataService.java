package com.osrsGoalTracker.data.internal;

import com.google.inject.Inject;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.data.UserDataService;
import com.osrsGoalTracker.data.pojo.domain.User;

/**
 * Default implementation of the UserDataService interface.
 * This service provides methods to interact with user data in the data layer.
 */
public class DefaultUserDataService implements UserDataService {
    private final GoalTrackerDao goalTrackerDao;

    /**
     * Constructs a new DefaultUserDataService.
     *
     * @param goalTrackerDao The GoalTrackerDao instance to use for data operations
     */
    @Inject
    public DefaultUserDataService(GoalTrackerDao goalTrackerDao) {
        this.goalTrackerDao = goalTrackerDao;
    }

    @Override
    public User getUser(String userId) {
        return convertToUser(goalTrackerDao.getUser(userId));
    }

    @Override
    public User createUser(String email) {
        return convertToUser(goalTrackerDao.createUser(
                com.osrsGoalTracker.dao.goalTracker.entity.UserEntity.builder()
                        .email(email)
                        .build()));
    }

    @Override
    public PlayerEntity addPlayerToUser(String userId, String playerName) {
        return goalTrackerDao.addPlayerToUser(userId, playerName);
    }

    /**
     * Converts a persistence User object to a domain User object.
     *
     * @param userEntity The persistence User object to convert
     * @return The converted domain User object
     */
    private User convertToUser(com.osrsGoalTracker.dao.goalTracker.entity.UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
