package com.osrsGoalTracker.goal.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrsGoalTracker.goal.dao.GoalDao;
import com.osrsGoalTracker.goal.dao.impl.DynamoGoalDao;
import com.osrsGoalTracker.goal.dao.impl.DynamoItem.DynamoGoalMetadataItem;
import com.osrsGoalTracker.goal.dao.impl.DynamoItem.DynamoGoalProgressItem;
import com.osrsGoalTracker.goal.repository.GoalRepository;
import com.osrsGoalTracker.goal.repository.impl.GoalRepositoryImpl;
import com.osrsGoalTracker.goal.service.GoalService;
import com.osrsGoalTracker.goal.service.impl.GoalServiceImpl;
import com.osrsGoalTracker.shared.di.SharedDynamoDbModule;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for goal-related bindings.
 */
public class GoalModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SharedDynamoDbModule());
        bind(GoalService.class).to(GoalServiceImpl.class);
        bind(GoalRepository.class).to(GoalRepositoryImpl.class);
        bind(GoalDao.class).to(DynamoGoalDao.class);
    }

    @Provides
    @Singleton
    DynamoGoalDao provideGoalDao(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        String tableName = System.getenv("GOAL_TRACKER_TABLE_NAME");
        DynamoDbTable<DynamoGoalMetadataItem> metadataTable = enhancedClient.table(
                tableName,
                TableSchema.fromClass(DynamoGoalMetadataItem.class));
        DynamoDbTable<DynamoGoalProgressItem> progressTable = enhancedClient.table(
                tableName,
                TableSchema.fromClass(DynamoGoalProgressItem.class));

        return new DynamoGoalDao(dynamoDbClient, metadataTable, progressTable);
    }
}