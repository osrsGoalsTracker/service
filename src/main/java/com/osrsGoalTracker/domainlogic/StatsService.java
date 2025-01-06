package com.osrsGoalTracker.domainlogic;

import com.osrsGoalTracker.service.pojo.sao.PlayerStatsResponse;

/**
 * Domain logic service interface for retrieving OSRS player stats.
 */
public interface StatsService {
    /**
     * Retrieves stats for a player from the OSRS hiscores.
     *
     * @param username The RuneScape name of the player
     * @return The player's stats
     * @throws RuntimeException if there's an error fetching the stats
     */
    PlayerStatsResponse getPlayerStats(String username);
}
