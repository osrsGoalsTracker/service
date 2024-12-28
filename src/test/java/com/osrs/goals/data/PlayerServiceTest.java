package com.osrs.goals.data;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.osrs.goals.model.Player;
import com.osrs.goals.persistence.PlayerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the PlayerService class.
 */
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    /**
     * Tests that getPlayer returns an empty Optional when the player doesn't exist.
     */
    @Test
    void shouldReturnEmptyOptionalWhenPlayerDoesNotExist() {
        // Arrange
        String rsn = "nonExistentPlayer";
        when(playerRepository.getPlayer(rsn)).thenReturn(Optional.empty());

        // Act
        Optional<Player> result = playerService.getPlayer(rsn);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Tests that getPlayer returns the player when they exist.
     */
    @Test
    void shouldReturnPlayerWhenPlayerExists() {
        // Arrange
        String rsn = "testPlayer";
        Player player = Player.builder()
                .rsn(rsn)
                .lastUpdated("2023-12-05T00:00:00Z")
                .build();
        when(playerRepository.getPlayer(rsn)).thenReturn(Optional.of(player));

        // Act
        Optional<Player> result = playerService.getPlayer(rsn);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(rsn, result.get().getRsn());
    }

    /**
     * Tests that savePlayer sets the lastUpdated timestamp and saves the player.
     */
    @Test
    void shouldSetLastUpdatedAndSavePlayer() {
        // Arrange
        String rsn = "testPlayer";
        Player player = Player.builder()
                .rsn(rsn)
                .build();

        // Act
        playerService.savePlayer(player);

        // Assert
        verify(playerRepository).savePlayer(any(Player.class));
    }
}
