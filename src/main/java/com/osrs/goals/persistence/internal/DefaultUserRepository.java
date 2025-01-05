package com.osrs.goals.persistence.internal;

import com.google.inject.Inject;
import com.osrs.goals.persistence.UserRepository;
import com.osrs.goals.persistence.pojo.dao.User;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.UserEntity;

import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

/**
 * Default implementation of UserRepository.
 * This class handles persistence operations for User entities using the
 * GoalTrackerDao.
 */
public class DefaultUserRepository implements UserRepository {
    private final GoalTrackerDao goalTrackerDao;

    /**
     * Constructs a new DefaultUserRepository.
     *
     * @param goalTrackerDao The GoalTrackerDao instance to use for persistence
     *                       operations
     */
    @Inject
    public DefaultUserRepository(GoalTrackerDao goalTrackerDao) {
        this.goalTrackerDao = goalTrackerDao;
    }

    @Override
    public User getUser(String userId) throws ResourceNotFoundException {
        try {
            UserEntity userEntity = goalTrackerDao.getUser(userId);
            return convertToUser(userEntity);
        } catch (Exception e) {
            throw ResourceNotFoundException.builder()
                    .message("User not found with ID: " + userId)
                    .cause(e)
                    .build();
        }
    }

    /**
     * Converts a UserEntity from the persistence layer to a User domain object.
     *
     * @param userEntity The UserEntity to convert
     * @return The converted User object
     */
    private User convertToUser(UserEntity userEntity) {
        return User.builder()
                .withUserId(userEntity.getUserId())
                .withEmail(userEntity.getEmail())
                .withCreatedAt(userEntity.getCreatedAt())
                .withUpdatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
