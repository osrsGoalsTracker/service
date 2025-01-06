package com.osrsGoalTracker.service.pojo.sao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for the getUser Lambda function.
 * Contains the user ID to retrieve.
 */
@Data
@NoArgsConstructor
public class GetUserRequest {
    private String userId;
}
