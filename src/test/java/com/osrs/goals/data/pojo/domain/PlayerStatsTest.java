package com.osrs.goals.data.pojo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.Skill;

/**
 * Tests for the PlayerStats domain model.
 */
class PlayerStatsTest {

    @Test
    void builderShouldCreatePlayerStats() {
        // Given
        String username = "testUser";
        Map<String, Skill> skills = new HashMap<>();
        Map<String, Activity> activities = new HashMap<>();

        // When
        PlayerStats stats = PlayerStats.builder()
                .username(username)
                .skills(skills)
                .activities(activities)
                .build();

        // Then
        assertNotNull(stats);
        assertEquals(username, stats.getUsername());
        assertEquals(skills, stats.getSkills());
        assertEquals(activities, stats.getActivities());
    }
}
// Added newline here
