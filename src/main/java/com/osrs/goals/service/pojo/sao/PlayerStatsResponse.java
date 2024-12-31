package com.osrs.goals.service.pojo.sao;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object containing player statistics.
 * This class represents the service layer response structure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
