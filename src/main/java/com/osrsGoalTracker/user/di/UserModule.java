package com.osrsGoalTracker.user.di;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.user.repository.impl.UserRepositoryImpl;
import com.osrsGoalTracker.user.repository.UserRepository;
import com.osrsGoalTracker.user.service.UserService;
import com.osrsGoalTracker.user.service.impl.UserServiceImpl;

/**
 * Guice module for user-related bindings.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
    }
}
