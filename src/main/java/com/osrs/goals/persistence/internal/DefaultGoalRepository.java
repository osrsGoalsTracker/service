package com.osrs.goals.persistence.internal;

import java.util.UUID;

import com.google.inject.Inject;
import com.osrs.goals.persistence.GoalRepository;
import com.osrs.goals.persistence.pojo.dao.Goal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the GoalRepository interface.
 * Handles persistence operations for goals using JPA.
 */
@Log4j2
public class DefaultGoalRepository implements GoalRepository {
    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructs a new DefaultGoalRepository with the required dependencies.
     *
     * @param entityManagerFactory The factory for creating EntityManager instances
     */
    @Inject
    public DefaultGoalRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Goal createGoal(UUID id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Goal goal = Goal.builder()
                    .id(id)
                    .build();

            entityManager.getTransaction().begin();
            entityManager.persist(goal);
            entityManager.getTransaction().commit();

            return goal;
        } catch (Exception e) {
            log.error("Failed to create goal with id {}: {}", id, e.getMessage());
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Error creating goal", e);
        } finally {
            entityManager.close();
        }
    }
}
