package com.osrs.goals.service.pojo.sao;

import java.time.LocalDateTime;

/**
 * Response object for the getUser Lambda function.
 * Contains user metadata including ID, email, and timestamps.
 */
public final class GetUserResponse {
    private final String userId;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private GetUserResponse(Builder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the user's email address.
     *
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the timestamp when the user was created.
     *
     * @return The creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the timestamp when the user was last updated.
     *
     * @return The last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Creates a new builder instance.
     *
     * @return A new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for creating GetUserResponse instances.
     */
    public static class Builder {
        private String userId;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        /**
         * Sets the user ID.
         *
         * @param userId The user ID to set
         * @return This builder instance
         */
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the email address.
         *
         * @param email The email to set
         * @return This builder instance
         */
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the creation timestamp.
         *
         * @param createdAt The creation timestamp to set
         * @return This builder instance
         */
        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets the last update timestamp.
         *
         * @param updatedAt The last update timestamp to set
         * @return This builder instance
         */
        public Builder withUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * Builds a new GetUserResponse instance.
         *
         * @return A new GetUserResponse instance
         */
        public GetUserResponse build() {
            return new GetUserResponse(this);
        }
    }
}