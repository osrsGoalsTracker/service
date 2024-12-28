package com.osrs.goals.external.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.osrs.goals.external.OsrsStatsService;
import com.osrs_hiscores_fetcher.api.OsrsHiscoresPlayerFetcher;
import com.osrs_hiscores_fetcher.api.models.OsrsPlayer;

import lombok.extern.log4j.Log4j2;

/**
 * Default implementation of the OSRS stats service.
 */
@Singleton
@Log4j2
public class DefaultOsrsStatsService implements OsrsStatsService {
    private final OsrsHiscoresPlayerFetcher hiscoresFetcher;

    /**
     * Constructor.
     *
     * @param hiscoresFetcher The hiscores fetcher to use
     */
    @Inject
    public DefaultOsrsStatsService(OsrsHiscoresPlayerFetcher hiscoresFetcher) {
        this.hiscoresFetcher = hiscoresFetcher;
    }

    @Override
    public Optional<OsrsPlayer> getPlayerStats(String rsn) {
        try {
            return Optional.ofNullable(hiscoresFetcher.getPlayerByRsn(rsn));
        } catch (Exception e) {
            log.error("Error fetching stats for player: {}", rsn, e);
            return Optional.empty();
        }
    }
} 
