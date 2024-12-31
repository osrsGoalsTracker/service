package com.osrs.goals.domainlogic.internal;

import com.google.inject.Inject;
import com.osrs.goals.data.StatsDataService;
import com.osrs.goals.data.pojo.domain.PlayerStats;
import com.osrs.goals.domainlogic.StatsService;
import com.osrs.goals.service.pojo.sao.PlayerStatsResponse;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the StatsService interface.
 * This class implements the domain logic for retrieving and processing player statistics,
 * coordinating between the service layer and data layer.
 */
@Log4j2
public class DefaultStatsService implements StatsService {
    private final StatsDataService statsDataService;

    /**
     * Constructs a new DefaultStatsService.
     *
     * @param statsDataService The service for retrieving player statistics data
     */
    @Inject
    public DefaultStatsService(StatsDataService statsDataService) {
        this.statsDataService = statsDataService;
    }

    @Override
    public PlayerStatsResponse getPlayerStats(String username) {
        log.info("Getting player stats for username: {}", username);
        PlayerStats stats = statsDataService.getPlayerStats(username);
        return convertToResponse(stats);
    }

    /**
     * Converts the domain model to the service layer response.
     *
     * @param stats The domain model containing player statistics
     * @return The converted service layer response
     */
    private PlayerStatsResponse convertToResponse(PlayerStats stats) {
        return PlayerStatsResponse.builder()
                .username(stats.getUsername())
                .skills(stats.getSkills())
                .activities(stats.getActivities())
                .build();
    }
}
