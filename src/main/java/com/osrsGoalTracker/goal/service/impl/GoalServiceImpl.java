package com.osrsGoalTracker.goal.service.impl;

import java.time.Instant;

import com.google.inject.Inject;
import com.osrsGoalTracker.goal.model.Goal;
import com.osrsGoalTracker.goal.repository.GoalRepository;
import com.osrsGoalTracker.goal.service.GoalService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the GoalService interface.
 */
@Slf4j
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    /**
     * Constructor for GoalServiceImpl.
     * 
     * @param goalRepository The goal repository.
     */
    @Inject
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    /**
     * Creates a new goal with the current progress.
     *
     * @param goal            The goal to create
     * @param currentProgress The current progress towards the goal
     * @return The created goal
     * @throws IllegalArgumentException if the goal is invalid
     */
    @Override
    public Goal createGoal(Goal goal, long currentProgress) {
        validateGoal(goal, currentProgress);
        log.info("Creating goal for user {} targeting {}", goal.getUserId(), goal.getTargetAttribute());

        return goalRepository.createGoal(goal, currentProgress);
    }

    private void validateGoal(Goal goal, long currentProgress) {
        validateGoalNotNull(goal);
        validateRequiredFields(goal);
        validateTargetValue(goal.getTargetValue());
        validateCurrentProgress(currentProgress);
        validateTargetDate(goal.getTargetDate());
    }

    private void validateGoalNotNull(Goal goal) {
        if (goal == null) {
            throw new IllegalArgumentException("goal cannot be null");
        }
    }

    private void validateRequiredFields(Goal goal) {
        validateField(goal.getUserId(), "userId");
        validateField(goal.getCharacterName(), "characterName");
        validateField(goal.getTargetAttribute(), "targetAttribute");
    }

    private void validateField(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    private void validateTargetValue(long targetValue) {
        if (targetValue <= 0) {
            throw new IllegalArgumentException("targetValue must be greater than 0");
        }
    }

    private void validateCurrentProgress(long currentProgress) {
        if (currentProgress < 0) {
            throw new IllegalArgumentException("currentProgress cannot be negative");
        }
    }

    private void validateTargetDate(Instant targetDate) {
        if (targetDate == null) {
            throw new IllegalArgumentException("targetDate cannot be null");
        }
    }
}