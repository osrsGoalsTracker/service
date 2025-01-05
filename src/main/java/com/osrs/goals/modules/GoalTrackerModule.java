package com.osrs.goals.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrs.goals.data.GoalTrackerDataService;
import com.osrs.goals.data.internal.DefaultGoalTrackerDataService;
import com.osrs.goals.domainlogic.GoalTrackerService;
import com.osrs.goals.domainlogic.internal.DefaultGoalTrackerService;
import com.osrsGoalTracker.dao.goalTracker.module.GoalTrackerDaoModule;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for configuring user-related dependencies.
 * This module provides bindings for user-related services and repositories.
 */
public class GoalTrackerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GoalTrackerDaoModule());

        bind(GoalTrackerService.class).to(DefaultGoalTrackerService.class);
        bind(GoalTrackerDataService.class).to(DefaultGoalTrackerDataService.class);
    }

    @Provides
    @Singleton
    DynamoDbClient provideDynamoDbClient() {
        return DynamoDbClient.create();
    }
}
