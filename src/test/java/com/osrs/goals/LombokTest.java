package com.osrs.goals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.osrs.goals.data.pojo.domain.PlayerStats;

/**
 * Test class to verify Lombok functionality.
 * This ensures that Lombok annotations are working correctly in the project.
 */
class LombokTest {

    /**
     * Tests that Lombok's @Builder annotation works correctly.
     * Verifies object creation and field access.
     */
    @Test
    void testLombokBuilder() {
        String username = "testUser";
        PlayerStats stats = PlayerStats.builder()
                .username(username)
                .build();

        assertNotNull(stats);
        assertEquals(username, stats.getUsername());
    }
}
