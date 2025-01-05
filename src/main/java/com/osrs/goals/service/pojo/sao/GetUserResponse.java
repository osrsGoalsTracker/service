package com.osrs.goals.service.pojo.sao;

import lombok.Getter;

/**
 * Response object for the getUser Lambda function.
 * Contains user metadata including ID, email, and timestamps.
 */
@Getter
public final class GetUserResponse {
    private final String userId;
    private final String email;
    private final String createdAt;
    private final String updatedAt;

    private GetUserResponse(Builder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
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
        private String createdAt;
        private String updatedAt;

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
        public Builder withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets the last update timestamp.
         *
         * @param updatedAt The last update timestamp to set
         * @return This builder instance
         */
        public Builder withUpdatedAt(String updatedAt) {
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
