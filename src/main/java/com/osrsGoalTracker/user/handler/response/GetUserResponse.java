package com.osrsGoalTracker.user.handler.response;

import lombok.Builder;
import lombok.Value;

/**
 * Response object for user retrieval operations.
 */
@Value
@Builder
public class GetUserResponse {
    /**
     * The unique identifier of the user.
     */
    private final String userId;

    /**
     * The email address of the user.
     */
    private final String email;
}
