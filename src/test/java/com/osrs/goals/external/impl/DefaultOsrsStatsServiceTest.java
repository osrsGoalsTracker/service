package com.osrs.goals.external.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.osrs_hiscores_fetcher.api.OsrsHiscoresPlayerFetcher;
import com.osrs_hiscores_fetcher.api.models.Activity;
import com.osrs_hiscores_fetcher.api.models.OsrsPlayer;
import com.osrs_hiscores_fetcher.api.models.Skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the DefaultOsrsStatsService class.
 */
public class DefaultOsrsStatsServiceTest {
    @Mock
    private OsrsHiscoresPlayerFetcher hiscoresFetcher;

    private DefaultOsrsStatsService service;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new DefaultOsrsStatsService(hiscoresFetcher);
    }

    /**
     * Tests successful retrieval of player stats.
     */
    @Test
    void shouldReturnStatsWhenPlayerExists() throws Exception {
        // Arrange
        String rsn = "testPlayer";
        OsrsPlayer mockData = new OsrsPlayer(rsn, new ArrayList<Skill>(), new ArrayList<Activity>());
        when(hiscoresFetcher.getPlayerByRsn(rsn)).thenReturn(mockData);

        // Act
        Optional<OsrsPlayer> result = service.getPlayerStats(rsn);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockData, result.get());
    }

    /**
     * Tests handling of a request for a non-existent player.
     */
    @Test
    void shouldReturnEmptyWhenPlayerDoesNotExist() throws Exception {
        // Arrange
        String rsn = "nonExistentPlayer";
        when(hiscoresFetcher.getPlayerByRsn(rsn)).thenReturn(null);

        // Act
        Optional<OsrsPlayer> result = service.getPlayerStats(rsn);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Tests handling of an error during stats retrieval.
     */
    @Test
    void shouldReturnEmptyWhenErrorOccurs() throws Exception {
        // Arrange
        String rsn = "errorPlayer";
        when(hiscoresFetcher.getPlayerByRsn(rsn)).thenThrow(new RuntimeException("Test error"));

        // Act
        Optional<OsrsPlayer> result = service.getPlayerStats(rsn);

        // Assert
        assertTrue(result.isEmpty());
    }
} 
