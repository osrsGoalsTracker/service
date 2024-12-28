package com.osrs.goals.external;

import java.util.Optional;

import com.osrs_hiscores_fetcher.api.models.OsrsPlayer;

/**
 * Service for interacting with OSRS hiscores.
 */
public interface OsrsStatsService {
    /**
     * Get player stats from OSRS hiscores.
     *
     * @param rsn The player's RSN
     * @return The player's stats, or empty if not found
     */
    Optional<OsrsPlayer> getPlayerStats(String rsn);
} 
