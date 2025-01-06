package com.osrsGoalTracker.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrsGoalTracker.data.UserDataService;
import com.osrsGoalTracker.data.internal.DefaultUserDataService;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.domainlogic.internal.DefaultUserService;
import com.osrsGoalTracker.persistence.UserRepository;
import com.osrsGoalTracker.persistence.internal.DefaultUserRepository;
import com.osrsGoalTracker.dao.goalTracker.module.GoalTrackerDaoModule;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for configuring user-related dependencies.
 * This module provides bindings for user-related services and repositories.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GoalTrackerDaoModule());

        bind(UserRepository.class).to(DefaultUserRepository.class);
        bind(UserDataService.class).to(DefaultUserDataService.class);
        bind(UserService.class).to(DefaultUserService.class);
    }

    @Provides
    @Singleton
    DynamoDbClient provideDynamoDbClient() {
        return DynamoDbClient.create();
    }
}
