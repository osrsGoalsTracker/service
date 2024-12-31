package com.osrs.goals.persistence;

import java.util.UUID;

import com.osrs.goals.persistence.pojo.dao.Goal;

/**
 * Repository interface for goal persistence operations.
 */
public interface GoalRepository {
    /**
     * Creates a new goal with the given ID.
     *
     * @param id The UUID for the new goal
     * @return The created goal
     * @throws RuntimeException if there's an error creating the goal
     */
    Goal createGoal(UUID id);
}
