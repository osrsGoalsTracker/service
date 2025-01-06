package com.osrsGoalTracker.domainlogic.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osrsGoalTracker.data.StatsDataService;
import com.osrsGoalTracker.data.pojo.domain.PlayerStats;
import com.osrsGoalTracker.service.pojo.sao.PlayerStatsResponse;

@ExtendWith(MockitoExtension.class)
class DefaultStatsServiceTest {

    @Mock
    private StatsDataService statsDataService;

    @InjectMocks
    private DefaultStatsService statsService;

    @Test
    void getPlayerStatsShouldReturnPlayerStats() {
        // Given
        String username = "testUser";
        PlayerStats mockStats = PlayerStats.builder().username(username).build();
        when(statsDataService.getPlayerStats(username)).thenReturn(mockStats);

        // When
        PlayerStatsResponse result = statsService.getPlayerStats(username);

        // Then
        assertEquals(username, result.getUsername());
    }
}
