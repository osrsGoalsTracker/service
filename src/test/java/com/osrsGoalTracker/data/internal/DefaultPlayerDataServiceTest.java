package com.osrsGoalTracker.data.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultPlayerDataServiceTest {
    private GoalTrackerDao goalTrackerDao;
    private DefaultPlayerDataService playerDataService;

    @BeforeEach
    void setUp() {
        goalTrackerDao = mock(GoalTrackerDao.class);
        playerDataService = new DefaultPlayerDataService(goalTrackerDao);
    }

    @Test
    void getPlayersForUserShouldReturnPlayers() {
        String userId = "testUser";
        List<PlayerEntity> expectedPlayers = Arrays.asList(
                createPlayerEntity("player1"),
                createPlayerEntity("player2"));
        when(goalTrackerDao.getPlayersForUser(userId)).thenReturn(expectedPlayers);

        List<PlayerEntity> actualPlayers = playerDataService.getPlayersForUser(userId);

        assertEquals(expectedPlayers, actualPlayers);
        verify(goalTrackerDao).getPlayersForUser(userId);
    }

    @Test
    void getPlayersForUserShouldTrimUserId() {
        String userId = "  testUser  ";
        String trimmedUserId = "testUser";
        List<PlayerEntity> expectedPlayers = Arrays.asList(
                createPlayerEntity("player1"),
                createPlayerEntity("player2"));
        when(goalTrackerDao.getPlayersForUser(trimmedUserId)).thenReturn(expectedPlayers);

        List<PlayerEntity> actualPlayers = playerDataService.getPlayersForUser(userId);

        assertEquals(expectedPlayers, actualPlayers);
        verify(goalTrackerDao).getPlayersForUser(trimmedUserId);
    }

    @Test
    void getPlayersForUserShouldThrowExceptionWhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> playerDataService.getPlayersForUser(null));
    }

    @Test
    void getPlayersForUserShouldThrowExceptionWhenUserIdIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> playerDataService.getPlayersForUser(""));
    }

    @Test
    void getPlayersForUserShouldThrowExceptionWhenUserIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> playerDataService.getPlayersForUser("   "));
    }

    private PlayerEntity createPlayerEntity(String name) {
        return PlayerEntity.builder()
                .name(name)
                .build();
    }
}