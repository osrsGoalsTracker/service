package com.osrsGoalTracker.data.internal;

import com.google.inject.Inject;
import com.osrsGoalTracker.data.StatsDataService;
import com.osrsGoalTracker.data.pojo.domain.PlayerStats;
import com.osrsGoalTracker.dependency.HiscoresClient;
import com.osrsGoalTracker.dependency.pojo.external.PlayerStatsResponse;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the StatsDataService interface.
 * This class coordinates between external dependencies (OSRS Hiscores)
 * and domain models, handling data conversion and error handling.
 */
@Log4j2
public class DefaultStatsDataService implements StatsDataService {
    private final HiscoresClient hiscoresClient;

    /**
     * Constructs a new DefaultStatsDataService.
     *
     * @param hiscoresClient The client for interacting with OSRS Hiscores
     */
    @Inject
    public DefaultStatsDataService(HiscoresClient hiscoresClient) {
        this.hiscoresClient = hiscoresClient;
    }

    @Override
    public PlayerStats getPlayerStats(String username) {
        try {
            PlayerStatsResponse response = hiscoresClient.getPlayerStats(username);
            return convertToPlayerStats(response, username);
        } catch (Exception e) {
            log.error("Failed to fetch player stats for {}: {}", username, e.getMessage());
            throw new RuntimeException("Error fetching player stats", e);
        }
    }

    /**
     * Converts the external API response to the domain model.
     *
     * @param response The response from the OSRS Hiscores API
     * @param username The username of the player
     * @return The converted domain model
     */
    private PlayerStats convertToPlayerStats(PlayerStatsResponse response, String username) {
        return PlayerStats.builder()
                .username(username)
                .skills(response.getSkills())
                .activities(response.getActivities())
                .build();
    }
}
