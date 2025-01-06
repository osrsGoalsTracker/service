package com.osrsGoalTracker.data.internal;

import com.google.inject.Inject;
import com.osrsGoalTracker.data.UserDataService;
import com.osrsGoalTracker.data.pojo.domain.User;
import com.osrsGoalTracker.persistence.UserRepository;

/**
 * Default implementation of the UserDataService interface.
 * This service provides methods to interact with user data in the data layer.
 */
public class DefaultUserDataService implements UserDataService {
    private final UserRepository userRepository;

    /**
     * Constructs a new DefaultUserDataService.
     *
     * @param userRepository The UserRepository instance to use for data operations
     */
    @Inject
    public DefaultUserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId) {
        return convertToUser(userRepository.getUser(userId));
    }

    @Override
    public User createUser(String email) {
        return convertToUser(userRepository.createUser(email));
    }

    /**
     * Converts a persistence User object to a domain User object.
     *
     * @param persistenceUser The persistence User object to convert
     * @return The converted domain User object
     */
    private User convertToUser(com.osrsGoalTracker.persistence.pojo.dao.User persistenceUser) {
        return User.builder()
                .userId(persistenceUser.getUserId())
                .email(persistenceUser.getEmail())
                .createdAt(persistenceUser.getCreatedAt())
                .updatedAt(persistenceUser.getUpdatedAt())
                .build();
    }
}
