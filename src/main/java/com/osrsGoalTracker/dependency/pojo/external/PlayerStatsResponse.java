package com.osrsGoalTracker.dependency.pojo.external;

import java.util.Map;

import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.Skill;

import lombok.Builder;
import lombok.Value;

/**
 * Response object containing player statistics from the OSRS Hiscores API.
 * This class represents the external data structure used for communication with the API.
 */
@Value
@Builder
public class PlayerStatsResponse {
    /**
     * Map of skill names to their corresponding skill data.
     */
    private Map<String, Skill> skills;

    /**
     * Map of activity names to their corresponding activity data.
     */
    private Map<String, Activity> activities;
}
