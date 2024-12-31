package com.osrs.goals.data.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osrs.goals.data.pojo.domain.PlayerStats;
import com.osrs.goals.dependency.HiscoresClient;
import com.osrs.goals.dependency.pojo.external.PlayerStatsResponse;

@ExtendWith(MockitoExtension.class)
class DefaultStatsDataServiceTest {

    @Mock
    private HiscoresClient hiscoresClient;

    @InjectMocks
    private DefaultStatsDataService statsDataService;

    @Test
    void getPlayerStatsShouldReturnPlayerStats() {
        // Given
        String username = "testUser";
        PlayerStatsResponse mockResponse = PlayerStatsResponse.builder().build();
        when(hiscoresClient.getPlayerStats(username)).thenReturn(mockResponse);

        // When
        PlayerStats result = statsDataService.getPlayerStats(username);

        // Then
        assertEquals(username, result.getUsername());
    }

    @Test
    void getPlayerStatsShouldThrowExceptionWhenClientFails() {
        // Given
        String username = "testUser";
        RuntimeException expectedException = new RuntimeException("Test error");
        when(hiscoresClient.getPlayerStats(username)).thenThrow(expectedException);

        // When/Then
        assertThrows(RuntimeException.class, () -> statsDataService.getPlayerStats(username));
    }
}

