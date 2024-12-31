package com.osrs.goals.data;

import java.util.UUID;

import com.osrs.goals.data.pojo.domain.Goal;

/**
 * Data layer service interface for goal operations.
 * This layer coordinates between domain logic and persistence.
 */
public interface GoalDataService {
    /**
     * Creates a new goal with the given ID.
     *
     * @param id The UUID for the new goal
     * @return The created goal
     * @throws RuntimeException if there's an error creating the goal
     */
    Goal createGoal(UUID id);
}
// Added newline below
