package com.osrsGoalTracker.data.pojo.domain;

import java.util.Map;

import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.Skill;

import lombok.Builder;
import lombok.Value;

/**
 * Domain model representing a player's OSRS statistics.
 * This class contains the core data structure for player stats within the application.
 */
@Value
@Builder
public class PlayerStats {
    /**
     * The RuneScape username of the player.
     */
    private String username;

    /**
     * Map of skill names to their corresponding skill data.
     */
    private Map<String, Skill> skills;

    /**
     * Map of activity names to their corresponding activity data.
     */
    private Map<String, Activity> activities;
}
