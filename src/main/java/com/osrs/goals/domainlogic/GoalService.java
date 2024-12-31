package com.osrs.goals.domainlogic;

import com.osrs.goals.service.pojo.sao.GoalCreatorResponse;

/**
 * Service for managing goals.
 */
public interface GoalService {
    /**
     * Creates a new goal.
     *
     * @return The created goal
     */
    GoalCreatorResponse createNewGoal();
}
