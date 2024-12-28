package com.osrs.goals.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.external.OsrsStatsService;
import com.osrs.goals.external.impl.DefaultOsrsStatsService;
import com.osrs.goals.persistence.PlayerRepository;
import com.osrs.goals.persistence.impl.DynamoDbPlayerRepository;
import com.osrs_hiscores_fetcher.api.OsrsHiscoresPlayerFetcher;
import com.osrs_hiscores_fetcher.impl.fetcher.OsrsHiscoresPlayerFetcherImpl;
import com.osrs_hiscores_fetcher.impl.service.HttpService;
import com.osrs_hiscores_fetcher.impl.service.impl.HttpServiceImpl;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for player-related dependencies.
 */
public class PlayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlayerRepository.class).to(DynamoDbPlayerRepository.class).in(Singleton.class);
        bind(OsrsStatsService.class).to(DefaultOsrsStatsService.class).in(Singleton.class);
        bind(PlayerService.class).in(Singleton.class);
        bind(OsrsHiscoresPlayerFetcher.class).to(OsrsHiscoresPlayerFetcherImpl.class).in(Singleton.class);
        bind(HttpService.class).to(HttpServiceImpl.class).in(Singleton.class);
    }

    /**
     * Provides a DynamoDbClient instance.
     *
     * @return The DynamoDbClient instance
     */
    @Provides
    @Singleton
    DynamoDbClient provideDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.US_WEST_2)
                .build();
    }
} 
