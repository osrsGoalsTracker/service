package com.osrsGoalTracker.modules;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.repository.UserRepository;
import com.osrsGoalTracker.repository.impl.UserRepositoryImpl;
import com.osrsGoalTracker.service.UserService;
import com.osrsGoalTracker.service.impl.UserServiceImpl;

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
