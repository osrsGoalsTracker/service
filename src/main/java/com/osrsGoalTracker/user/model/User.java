package com.osrsGoalTracker.user.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

/**
 * Model representing a user in the system.
 */
@Value
@Builder
public class User {
    /**
     * The unique identifier of the user.
     */
    private final String userId;

    /**
     * The user's email address.
     */
    private final String email;

    /**
     * The timestamp when the user was created.
     */
    private final LocalDateTime createdAt;

    /**
     * The timestamp when the user was last updated.
     */
    private final LocalDateTime updatedAt;
}