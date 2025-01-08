package com.osrsGoalTracker.user.repository.impl;

import com.google.inject.Inject;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.UserEntity;
import com.osrsGoalTracker.user.model.User;
import com.osrsGoalTracker.user.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the UserRepository interface.
 */
@Log4j2
public class UserRepositoryImpl implements UserRepository {
    private final GoalTrackerDao goalTrackerDao;

    /**
     * Constructs a new UserRepositoryImpl.
     *
     * @param goalTrackerDao The GoalTrackerDao instance to use for data operations
     */
    @Inject
    public UserRepositoryImpl(GoalTrackerDao goalTrackerDao) {
        this.goalTrackerDao = goalTrackerDao;
    }

    @Override
    public User createUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String trimmedEmail = email.trim();
        log.info("Creating user with email: {}", trimmedEmail);

        UserEntity userEntity = UserEntity.builder()
                .email(trimmedEmail)
                .build();
        UserEntity createdUser = goalTrackerDao.createUser(userEntity);
        return convertToUser(createdUser);
    }

    @Override
    public User getUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String trimmedUserId = userId.trim();
        log.info("Getting user with ID: {}", trimmedUserId);

        UserEntity userEntity = goalTrackerDao.getUser(trimmedUserId);
        return convertToUser(userEntity);
    }

    private User convertToUser(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
