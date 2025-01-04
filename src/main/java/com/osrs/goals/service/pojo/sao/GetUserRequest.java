package com.osrs.goals.service.pojo.sao;

/**
 * Request object for the getUser Lambda function.
 * Contains the user ID to retrieve.
 */
public class GetUserRequest {
    private String userId;

    /**
     * Default constructor for JSON deserialization.
     */
    public GetUserRequest() {
    }

    /**
     * Gets the user ID to retrieve.
     *
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID to retrieve.
     *
     * @param userId The user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
