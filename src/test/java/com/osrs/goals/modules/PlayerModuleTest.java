package com.osrs.goals.modules;

import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrs.goals.data.PlayerService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the PlayerModule dependency injection configuration.
 */
public class PlayerModuleTest {

    /**
     * Tests that the injector can be created successfully and provides a PlayerService instance.
     */
    @Test
    public void shouldCreateInjectorSuccessfully() {
        // Act
        Injector injector = Guice.createInjector(new PlayerModule());
        PlayerService playerService = injector.getInstance(PlayerService.class);

        // Assert
        assertNotNull(playerService, "PlayerService should be successfully injected");
    }
} 
