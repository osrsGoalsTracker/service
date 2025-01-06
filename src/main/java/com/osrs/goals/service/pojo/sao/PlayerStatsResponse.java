package com.osrs.goals.service.pojo.sao;

import java.util.Map;

import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Response object containing player statistics.
 * This class represents the service layer response structure.
 */
@Value
@Builder
@AllArgsConstructor
public class PlayerStatsResponse {
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

