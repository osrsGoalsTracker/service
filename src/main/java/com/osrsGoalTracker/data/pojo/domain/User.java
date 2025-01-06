package com.osrsGoalTracker.data.pojo.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

/**
 * Represents a user entity in the domain layer.
 */
@Value
@Builder
public final class User {
    private final String userId;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
