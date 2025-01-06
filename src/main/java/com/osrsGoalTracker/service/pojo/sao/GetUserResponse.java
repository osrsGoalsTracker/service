package com.osrsGoalTracker.service.pojo.sao;

import lombok.Builder;
import lombok.Value;

/**
 * Response object for the getUser Lambda function.
 * Contains user metadata including ID, email, and timestamps.
 */
@Value
@Builder
public final class GetUserResponse {
    private final String userId;
    private final String email;
    private final String createdAt;
    private final String updatedAt;
}
