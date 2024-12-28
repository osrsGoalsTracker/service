package com.osrs.goals.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a RuneScape player with their basic information.
 */
@Data
@Builder
public class Player {
    /**
     * The RuneScape name (RSN) of the player.
     */
    private String rsn;

    /**
     * The timestamp when the player data was last updated.
     */
    private String lastUpdated;
}
