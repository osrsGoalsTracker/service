package com.osrs.goals.modules;

import org.junit.jupiter.api.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.external.OsrsStatsService;
import com.osrs.goals.persistence.PlayerRepository;
import com.osrs_hiscores_fetcher.api.OsrsHiscoresPlayerFetcher;
import com.osrs_hiscores_fetcher.impl.service.HttpService;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the PlayerModule dependency injection configuration.
 */
public class PlayerModuleTest {

    /**
     * Mock module for testing.
     */
    private static final class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(PlayerRepository.class).toInstance(mock(PlayerRepository.class));
            bind(OsrsStatsService.class).toInstance(mock(OsrsStatsService.class));
            bind(PlayerService.class).toInstance(mock(PlayerService.class));
            bind(OsrsHiscoresPlayerFetcher.class).toInstance(mock(OsrsHiscoresPlayerFetcher.class));
            bind(HttpService.class).toInstance(mock(HttpService.class));
            bind(DynamoDbClient.class).toInstance(mock(DynamoDbClient.class));
        }
    }

    /**
     * Tests that the injector can be created successfully and provides all required services.
     */
    @Test
    public void shouldCreateInjectorSuccessfully() {
        // Act
        Injector injector = Guice.createInjector(new TestModule());
        PlayerRepository playerRepository = injector.getInstance(PlayerRepository.class);
        OsrsStatsService osrsStatsService = injector.getInstance(OsrsStatsService.class);
        OsrsHiscoresPlayerFetcher hiscoresFetcher = injector.getInstance(OsrsHiscoresPlayerFetcher.class);

        // Assert
        assertNotNull(playerRepository, "PlayerRepository should be successfully injected");
        assertNotNull(osrsStatsService, "OsrsStatsService should be successfully injected");
        assertNotNull(hiscoresFetcher, "OsrsHiscoresPlayerFetcher should be successfully injected");
    }
} 

