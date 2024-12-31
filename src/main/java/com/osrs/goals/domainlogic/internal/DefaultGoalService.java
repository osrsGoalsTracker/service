package com.osrs.goals.domainlogic.internal;

import java.util.UUID;

import com.google.inject.Inject;
import com.osrs.goals.data.GoalDataService;
import com.osrs.goals.data.pojo.domain.Goal;
import com.osrs.goals.domainlogic.GoalService;
import com.osrs.goals.service.pojo.sao.CreateGoalResponse;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the GoalService interface.
 * Handles business logic for goal-related operations.
 */
@Log4j2
public class DefaultGoalService implements GoalService {
    private final GoalDataService goalDataService;

    /**
     * Constructs a new DefaultGoalService with the required dependencies.
     *
     * @param goalDataService The data service for goal operations
     */
    @Inject
    public DefaultGoalService(GoalDataService goalDataService) {
        this.goalDataService = goalDataService;
    }

    @Override
    public CreateGoalResponse createGoal() {
        try {
            UUID id = UUID.randomUUID();
            Goal goal = goalDataService.createGoal(id);
            return convertToResponse(goal);
        } catch (Exception e) {
            log.error("Failed to create goal: {}", e.getMessage());
            throw new RuntimeException("Error creating goal", e);
        }
    }

    private CreateGoalResponse convertToResponse(Goal goal) {
        return CreateGoalResponse.builder()
                .id(goal.getId())
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .build();
    }
}
