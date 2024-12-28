package com.osrs.goals.data;

import java.time.Instant;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.osrs.goals.model.Player;
import com.osrs.goals.persistence.PlayerRepository;

import lombok.extern.log4j.Log4j2;

/**
 * Service class for managing player data operations.
 */
@Log4j2
@Singleton
public class PlayerService {
    private final PlayerRepository playerRepository;

    /**
     * Constructs a new PlayerService with the given repository.
     *
     * @param playerRepository The repository to use for player data operations
     */
    @Inject
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Retrieves a player by their RuneScape name.
     *
     * @param rsn The RuneScape name to look up
     * @return An Optional containing the player if found, empty otherwise
     */
    public Optional<Player> getPlayer(String rsn) {
        log.info("Fetching player data for RSN: {}", rsn);
        return playerRepository.getPlayer(rsn);
    }

    /**
     * Saves or updates a player's data.
     *
     * @param player The player data to save
     */
    public void savePlayer(Player player) {
        log.info("Saving player data for RSN: {}", player.getRsn());
        player.setLastUpdated(Instant.now().toString());
        playerRepository.savePlayer(player);
    }
}
