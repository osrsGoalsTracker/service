package com.osrsGoalTracker.user.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.internal.ddb.DynamoGoalTrackerDao;
import com.osrsGoalTracker.shared.di.SharedDynamoDbModule;
import com.osrsGoalTracker.user.repository.UserRepository;
import com.osrsGoalTracker.user.repository.impl.UserRepositoryImpl;
import com.osrsGoalTracker.user.service.UserService;
import com.osrsGoalTracker.user.service.impl.UserServiceImpl;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for user-related bindings.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SharedDynamoDbModule());
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
    }

    @Provides
    @Singleton
    GoalTrackerDao provideGoalTrackerDao(DynamoDbClient dynamoDbClient) {
        return new DynamoGoalTrackerDao(dynamoDbClient, System.getenv("GOAL_TRACKER_TABLE_NAME"));
    }
}
