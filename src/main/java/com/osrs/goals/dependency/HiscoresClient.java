package com.osrs.goals.dependency;

import com.osrs.goals.dependency.pojo.external.PlayerStatsResponse;

/**
 * Client interface for interacting with the OSRS Hiscores API.
 */
public interface HiscoresClient {
    /**
     * Retrieves stats for a player from the OSRS hiscores.
     *
     * @param username The RuneScape name of the player
     * @return The player's stats
     * @throws RuntimeException if there's an error fetching the stats
     */
    PlayerStatsResponse getPlayerStats(String username);
}
