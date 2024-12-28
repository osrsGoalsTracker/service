package com.osrs.goals.persistence;

import java.util.Optional;

import com.osrs.goals.model.Player;

/**
 * Repository interface for player data persistence operations.
 */
public interface PlayerRepository {
    /**
     * Retrieves a player by their RuneScape name.
     *
     * @param rsn The RuneScape name to look up
     * @return An Optional containing the player if found, empty otherwise
     */
    Optional<Player> getPlayer(String rsn);

    /**
     * Saves or updates a player's data.
     *
     * @param player The player data to save
     */
    void savePlayer(Player player);
}
