package com.osrs.goals.modules;

import com.google.inject.AbstractModule;
import com.osrs.goals.data.UserDataService;
import com.osrs.goals.data.internal.DefaultUserDataService;
import com.osrs.goals.domainlogic.UserService;
import com.osrs.goals.domainlogic.internal.DefaultUserService;
import com.osrs.goals.persistence.UserRepository;
import com.osrs.goals.persistence.internal.DefaultUserRepository;
import com.osrsGoalTracker.goals.dao.module.GoalsDaoModule;

/**
 * Guice module for configuring user-related dependencies.
 * This module provides bindings for user-related services and repositories.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GoalsDaoModule());

        bind(UserRepository.class).to(DefaultUserRepository.class);
        bind(UserDataService.class).to(DefaultUserDataService.class);
        bind(UserService.class).to(DefaultUserService.class);
    }
}
