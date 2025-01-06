package com.osrsGoalTracker.data;

import com.osrsGoalTracker.data.pojo.domain.PlayerStats;

/**
 * Data layer service interface for retrieving player stats.
 * This layer coordinates between dependencies (external services) and persistence (storage).
 */
public interface StatsDataService {
    /**
     * Retrieves stats for a player, either from cache/storage or from the OSRS hiscores.
     *
     * @param rsn The RuneScape name of the player
     * @return The player's stats
     * @throws RuntimeException if there's an error fetching the stats
     */
    PlayerStats getPlayerStats(String rsn);
}
