package com.osrsGoalTracker.dependency.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.osrsGoalTracker.dependency.pojo.external.PlayerStatsResponse;
import com.osrshiscores.apiclient.OsrsApiClient;
import com.osrshiscores.apiclient.model.input.FetchOptions;
import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.OsrsPlayer;
import com.osrshiscores.apiclient.model.output.Skill;

class OsrsHiscoresClientTest {
    private OsrsApiClient mockApi;
    private OsrsHiscoresClient client;

    @BeforeEach
    void setUp() {
        mockApi = mock(OsrsApiClient.class);
        client = new OsrsHiscoresClient(mockApi);
    }

    @Test
    void getPlayerStatsShouldReturnPlayerStats() throws IOException {
        // Given
        String username = "testUser";
        OsrsPlayer mockPlayer = mock(OsrsPlayer.class);
        List<Skill> skills = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();

        when(mockApi.getPlayerByRsn(username, FetchOptions.defaults())).thenReturn(mockPlayer);
        when(mockPlayer.getSkills()).thenReturn(skills);
        when(mockPlayer.getActivities()).thenReturn(activities);

        // When
        PlayerStatsResponse result = client.getPlayerStats(username);

        // Then
        assertNotNull(result);
        Map<String, Skill> expectedSkills = skills.stream()
                .collect(java.util.stream.Collectors.toMap(Skill::getName, skill -> skill));
        Map<String, Activity> expectedActivities = activities.stream()
                .collect(java.util.stream.Collectors.toMap(Activity::getName, activity -> activity));
        assertEquals(expectedSkills, result.getSkills());
        assertEquals(expectedActivities, result.getActivities());
    }
}
