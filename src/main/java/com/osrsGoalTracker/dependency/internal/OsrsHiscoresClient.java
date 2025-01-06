package com.osrsGoalTracker.dependency.internal;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.osrsGoalTracker.dependency.HiscoresClient;
import com.osrsGoalTracker.dependency.pojo.external.PlayerStatsResponse;
import com.osrshiscores.apiclient.OsrsApiClient;
import com.osrshiscores.apiclient.model.input.FetchOptions;
import com.osrshiscores.apiclient.model.output.Activity;
import com.osrshiscores.apiclient.model.output.OsrsPlayer;
import com.osrshiscores.apiclient.model.output.Skill;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of the HiscoresClient interface that interacts with the OSRS Hiscores API.
 * This class is responsible for fetching player statistics from the OSRS Hiscores.
 */
@Log4j2
public class OsrsHiscoresClient implements HiscoresClient {
    private final OsrsApiClient osrsApiClient;

    /**
     * Constructs a new OsrsHiscoresClient.
     * Initializes the OSRS API client for making hiscores requests.
     */
    @Inject
    public OsrsHiscoresClient() {
        this.osrsApiClient = createApiClient();
    }

    /**
     * Constructor for testing purposes.
     * @param osrsApiClient The API client to use
     */
    public OsrsHiscoresClient(OsrsApiClient osrsApiClient) {
        this.osrsApiClient = osrsApiClient;
    }

    /**
     * Creates a new instance of the OSRS API client.
     * Protected method to allow for mocking in tests.
     *
     * @return A new instance of OsrsApiClient
     */
    protected OsrsApiClient createApiClient() {
        return new OsrsApiClient();
    }

    @Override
    public PlayerStatsResponse getPlayerStats(String username) {
        try {
            OsrsPlayer player = osrsApiClient.getPlayerByRsn(username, FetchOptions.defaults());
            return PlayerStatsResponse.builder()
                    .skills(convertSkillsToMap(player.getSkills()))
                    .activities(convertActivitiesToMap(player.getActivities()))
                    .build();
        } catch (Exception e) {
            log.error("Failed to fetch player stats for {}: {}", username, e.getMessage());
            throw new RuntimeException("Error fetching player stats", e);
        }
    }

    /**
     * Converts a list of skills to a map with skill names as keys.
     *
     * @param skills The list of skills to convert
     * @return A map of skill names to skill objects
     */
    private Map<String, Skill> convertSkillsToMap(java.util.List<Skill> skills) {
        return skills.stream()
                .collect(Collectors.toMap(Skill::getName, skill -> skill));
    }

    /**
     * Converts a list of activities to a map with activity names as keys.
     *
     * @param activities The list of activities to convert
     * @return A map of activity names to activity objects
     */
    private Map<String, Activity> convertActivitiesToMap(java.util.List<Activity> activities) {
        return activities.stream()
                .collect(Collectors.toMap(Activity::getName, activity -> activity));
    }
}

