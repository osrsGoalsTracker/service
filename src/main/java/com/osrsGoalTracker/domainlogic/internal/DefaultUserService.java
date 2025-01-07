package com.osrsGoalTracker.domainlogic.internal;

import com.google.inject.Inject;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.data.UserDataService;
import com.osrsGoalTracker.data.pojo.domain.User;
import com.osrsGoalTracker.domainlogic.UserService;

/**
 * Default implementation of the UserService interface.
 * This service provides methods to interact with user data in the domain layer.
 */
public class DefaultUserService implements UserService {
    private final UserDataService userDataService;

    /**
     * Constructs a new DefaultUserService.
     *
     * @param userDataService The UserDataService instance to use for data
     *                        operations
     */
    @Inject
    public DefaultUserService(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public User getUser(String userId) {
        return userDataService.getUser(userId);
    }

    @Override
    public User createUser(String email) {
        return userDataService.createUser(email);
    }

    @Override
    public PlayerEntity addPlayerToUser(String userId, String playerName) {
        return userDataService.addPlayerToUser(userId, playerName);
    }
}
