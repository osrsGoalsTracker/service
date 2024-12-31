package com.osrs.goals.data.internal;

import java.util.UUID;

import com.google.inject.Inject;
import com.osrs.goals.data.GoalDataService;
import com.osrs.goals.data.pojo.domain.Goal;
import com.osrs.goals.persistence.GoalRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the GoalDataService interface.
 * Handles data operations for goals using the repository layer.
 */
@Log4j2
public class DefaultGoalDataService implements GoalDataService {
    private final GoalRepository goalRepository;

    /**
     * Constructs a new DefaultGoalDataService with the required dependencies.
     *
     * @param goalRepository The repository for goal operations
     */
    @Inject
    public DefaultGoalDataService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public Goal createGoal(UUID id) {
        try {
            com.osrs.goals.persistence.pojo.dao.Goal persistedGoal = goalRepository.createGoal(id);
            return convertToGoal(persistedGoal);
        } catch (Exception e) {
            log.error("Failed to create goal with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error creating goal", e);
        }
    }

    private Goal convertToGoal(com.osrs.goals.persistence.pojo.dao.Goal persistedGoal) {
        return Goal.builder()
                .id(persistedGoal.getId().toString())
                .createdAt(persistedGoal.getCreatedAt().toString())
                .updatedAt(persistedGoal.getUpdatedAt().toString())
                .build();
    }
}
