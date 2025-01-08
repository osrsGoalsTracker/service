package com.osrsGoalTracker.external;

import com.osrsGoalTracker.models.CharacterHiscores;

/**
 * Interface for retrieving character hiscores from external sources.
 */
public interface HiscoresClient {
    /**
     * Retrieves the hiscores for a character.
     *
     * @param characterName The name of the character
     * @return The character's hiscores
     */
    CharacterHiscores getCharacterHiscores(String characterName);
}
