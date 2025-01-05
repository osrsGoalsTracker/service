package com.osrs.goals.persistence.pojo.dao;

import java.time.LocalDateTime;

import lombok.Getter;

/**
 * Represents a user entity in the persistence layer.
 */
@Getter
public final class User {
    private final String userId;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private User(Builder builder) {
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
     * Builder class for creating User instances.
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
         * Builds a new User instance.
         *
         * @return A new User instance
         */
        public User build() {
            return new User(this);
        }
    }
}
