package com.osrsGoalTracker.domainlogic.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.data.PlayerDataService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultPlayerServiceTest {
    private PlayerDataService playerDataService;
    private DefaultPlayerService playerService;

    @BeforeEach
    void setUp() {
        playerDataService = mock(PlayerDataService.class);
        playerService = new DefaultPlayerService(playerDataService);
    }

    @Test
    void getPlayersForUserShouldReturnPlayers() {
        String userId = "testUser";
        List<PlayerEntity> expectedPlayers = Arrays.asList(
                createPlayerEntity("player1"),
                createPlayerEntity("player2"));
        when(playerDataService.getPlayersForUser(userId)).thenReturn(expectedPlayers);

        List<PlayerEntity> actualPlayers = playerService.getPlayersForUser(userId);

        assertEquals(expectedPlayers, actualPlayers);
        verify(playerDataService).getPlayersForUser(userId);
    }

    @Test
    void getPlayersForUserShouldTrimUserId() {
        String userId = "  testUser  ";
        String trimmedUserId = "testUser";
        List<PlayerEntity> expectedPlayers = Arrays.asList(
                createPlayerEntity("player1"),
                createPlayerEntity("player2"));
        when(playerDataService.getPlayersForUser(trimmedUserId)).thenReturn(expectedPlayers);

        List<PlayerEntity> actualPlayers = playerService.getPlayersForUser(userId);

        assertEquals(expectedPlayers, actualPlayers);
        verify(playerDataService).getPlayersForUser(trimmedUserId);
    }

    @Test
    void getPlayersForUserShouldThrowExceptionWhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> playerService.getPlayersForUser(null));
    }

    @Test
    void getPlayersForUserShouldThrowExceptionWhenUserIdIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> playerService.getPlayersForUser(""));
    }

    @Test
    void getPlayersForUserShouldThrowExceptionWhenUserIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> playerService.getPlayersForUser("   "));
    }

    private PlayerEntity createPlayerEntity(String name) {
        return PlayerEntity.builder()
                .name(name)
                .build();
    }
}