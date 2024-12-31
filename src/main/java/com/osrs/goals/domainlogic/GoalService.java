package com.osrs.goals.domainlogic;

import com.osrs.goals.service.pojo.sao.CreateGoalResponse;

/**
 * Domain logic layer service interface for goal operations.
 */
public interface GoalService {
    /**
     * Creates a new goal.
     *
     * @return The created goal response
     * @throws RuntimeException if there's an error creating the goal
     */
    CreateGoalResponse createGoal();
}
