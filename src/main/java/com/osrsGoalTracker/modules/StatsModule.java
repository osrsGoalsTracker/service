package com.osrsGoalTracker.modules;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.data.StatsDataService;
import com.osrsGoalTracker.data.internal.DefaultStatsDataService;
import com.osrsGoalTracker.dependency.HiscoresClient;
import com.osrsGoalTracker.dependency.internal.OsrsHiscoresClient;
import com.osrsGoalTracker.domainlogic.StatsService;
import com.osrsGoalTracker.domainlogic.internal.DefaultStatsService;

/**
 * Guice module for configuring OSRS stats-related dependencies.
 */
public class StatsModule extends AbstractModule {
    /**
     * Configures the bindings for stats-related services and dependencies.
     * This includes the domain logic layer and data layer components.
     */
    @Override
    protected void configure() {
        // Bind domain logic layer
        bind(StatsService.class).to(DefaultStatsService.class);

        // Bind data layer
        bind(StatsDataService.class).to(DefaultStatsDataService.class);

        // Bind dependency layer
        bind(HiscoresClient.class).to(OsrsHiscoresClient.class);
    }
}
