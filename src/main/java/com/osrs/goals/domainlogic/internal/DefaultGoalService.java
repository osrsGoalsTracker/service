package com.osrs.goals.domainlogic.internal;

import java.util.UUID;

import com.google.inject.Inject;
import com.osrs.goals.data.GoalDataService;
import com.osrs.goals.data.pojo.domain.Goal;
import com.osrs.goals.domainlogic.GoalService;
import com.osrs.goals.service.pojo.sao.GoalCreatorResponse;

/**
 * Default implementation of the goal service.
 */
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
    public GoalCreatorResponse createNewGoal() {
        UUID id = UUID.randomUUID();
        Goal goal = goalDataService.createGoal(id);
        return convertToResponse(goal);
    }

    private GoalCreatorResponse convertToResponse(Goal goal) {
        return GoalCreatorResponse.builder()
                .id(goal.getId().toString())
                .createdAt(goal.getCreatedAt().toString())
                .build();
    }
}
