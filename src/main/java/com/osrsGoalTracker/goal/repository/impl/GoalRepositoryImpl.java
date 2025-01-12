package com.osrsGoalTracker.goal.repository.impl;

import com.google.inject.Inject;
import com.osrsGoalTracker.goal.dao.GoalDao;
import com.osrsGoalTracker.goal.dao.entity.GoalEntity;
import com.osrsGoalTracker.goal.model.Goal;
import com.osrsGoalTracker.goal.repository.GoalRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the GoalRepository interface.
 */
@Slf4j
public class GoalRepositoryImpl implements GoalRepository {
    private final GoalDao goalDao;

    /**
     * Constructor for GoalRepositoryImpl.
     *
     * @param goalDao The goal DAO
     */
    @Inject
    public GoalRepositoryImpl(GoalDao goalDao) {
        this.goalDao = goalDao;
    }

    @Override
    public Goal createGoal(Goal goal, long currentProgress) {
        log.info("Creating goal for user {} targeting {}", goal.getUserId(), goal.getTargetAttribute());

        GoalEntity goalEntity = GoalEntity.builder()
                .userId(goal.getUserId())
                .characterName(goal.getCharacterName())
                .targetAttribute(goal.getTargetAttribute())
                .targetType(goal.getTargetType())
                .targetValue(goal.getTargetValue())
                .targetDate(goal.getTargetDate())
                .notificationChannelType(goal.getNotificationChannelType())
                .frequency(goal.getFrequency())
                .build();

        GoalEntity createdEntity = goalDao.createGoal(goalEntity, currentProgress);
        return mapToGoal(createdEntity);
    }

    private Goal mapToGoal(GoalEntity entity) {
        return Goal.builder()
                .userId(entity.getUserId())
                .characterName(entity.getCharacterName())
                .targetAttribute(entity.getTargetAttribute())
                .targetType(entity.getTargetType())
                .targetValue(entity.getTargetValue())
                .targetDate(entity.getTargetDate())
                .notificationChannelType(entity.getNotificationChannelType())
                .frequency(entity.getFrequency())
                .build();
    }
}