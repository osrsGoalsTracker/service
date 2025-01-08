package com.osrsGoalTracker.character.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.osrsGoalTracker.character.model.Character;
import com.osrsGoalTracker.character.repository.CharacterRepository;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the CharacterRepository interface.
 */
@Log4j2
public class CharacterRepositoryImpl implements CharacterRepository {
    private final GoalTrackerDao goalTrackerDao;

    /**
     * Constructs a new DefaultCharacterRepository.
     *
     * @param goalTrackerDao The GoalTrackerDao instance to use for data operations
     */
    @Inject
    public CharacterRepositoryImpl(GoalTrackerDao goalTrackerDao) {
        this.goalTrackerDao = goalTrackerDao;
    }

    @Override
    public Character addCharacterToUser(String userId, String characterName) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (characterName == null || characterName.trim().isEmpty()) {
            throw new IllegalArgumentException("Character name cannot be null or empty");
        }

        String trimmedUserId = userId.trim();
        String trimmedCharacterName = characterName.trim();

        log.info("Adding character {} to user {}", trimmedCharacterName, trimmedUserId);

        PlayerEntity player = goalTrackerDao.addPlayerToUser(trimmedUserId, trimmedCharacterName);
        return convertToCharacter(player);
    }

    @Override
    public List<Character> getCharactersForUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            log.error("User ID cannot be null or empty");
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String trimmedUserId = userId.trim();
        log.info("Getting players for user {} from DAO", trimmedUserId);
        List<PlayerEntity> players = goalTrackerDao.getPlayersForUser(trimmedUserId);
        return convertToCharacters(players);
    }

    private Character convertToCharacter(PlayerEntity player) {
        return Character.builder()
                .name(player.getName())
                .userId(player.getUserId())
                .build();
    }

    private List<Character> convertToCharacters(List<PlayerEntity> players) {
        return players.stream()
                .map(this::convertToCharacter)
                .collect(Collectors.toList());
    }
}
