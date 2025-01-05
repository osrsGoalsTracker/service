package com.osrs.goals.service.pojo.sao;

import lombok.Getter;
import lombok.Setter;

/**
 * Request object for the getUser Lambda function.
 * Contains the user ID to retrieve.
 */
@Getter
@Setter
public class GetUserRequest {
    private String userId;

    /**
     * Default constructor for JSON deserialization.
     */
    public GetUserRequest() {
    }
}
