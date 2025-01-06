package com.osrsGoalTracker.service.pojo.sao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for the createUser Lambda function.
 * Contains the email address for the new user.
 */
@Data
@NoArgsConstructor
public class CreateUserRequest {
    private String email;
}