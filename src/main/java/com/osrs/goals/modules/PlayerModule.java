package com.osrs.goals.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.persistence.PlayerRepository;
import com.osrs.goals.persistence.PlayerRepositoryImpl.DynamoDbPlayerRepository;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for configuring player-related dependencies.
 */
public class PlayerModule extends AbstractModule {
    @Override
    protected void configure() {
        // No bindings needed in configure() as we're using @Provides methods
    }

    /**
     * Provides a singleton DynamoDB client configured for US West 2 region.
     *
     * @return The configured DynamoDB client
     */
    @Provides
    @Singleton
    DynamoDbClient provideDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.US_WEST_2)
                .build();
    }

    /**
     * Provides a singleton PlayerRepository implementation.
     *
     * @param dynamoDbClient The DynamoDB client to use
     * @return The configured PlayerRepository
     */
    @Provides
    @Singleton
    PlayerRepository providePlayerRepository(DynamoDbClient dynamoDbClient) {
        return new DynamoDbPlayerRepository(dynamoDbClient);
    }

    /**
     * Provides a singleton PlayerService.
     *
     * @param playerRepository The repository to use for player data
     * @return The configured PlayerService
     */
    @Provides
    @Singleton
    PlayerService providePlayerService(PlayerRepository playerRepository) {
        return new PlayerService(playerRepository);
    }
} 
