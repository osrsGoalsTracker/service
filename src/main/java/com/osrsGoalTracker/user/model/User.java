package com.osrsGoalTracker.user.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime createdAt;

    /**
     * The timestamp when the user was last updated.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime updatedAt;
}