package com.osrs.goals.domainlogic.internal;

import com.google.inject.Inject;
import com.osrs.goals.data.UserDataService;
import com.osrs.goals.data.pojo.domain.User;
import com.osrs.goals.domainlogic.UserService;

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
}
