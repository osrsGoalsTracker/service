package com.osrsGoalTracker.shared.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Shared module for providing DynamoDB client.
 */
public class SharedDynamoDbModule extends AbstractModule {
    @Provides
    @Singleton
    DynamoDbClient provideDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(System.getenv("AWS_REGION")))
                .build();
    }
}