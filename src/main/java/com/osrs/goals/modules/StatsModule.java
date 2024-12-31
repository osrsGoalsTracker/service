package com.osrs.goals.modules;

import com.google.inject.AbstractModule;
import com.osrs.goals.data.StatsDataService;
import com.osrs.goals.data.internal.DefaultStatsDataService;
import com.osrs.goals.dependency.HiscoresClient;
import com.osrs.goals.dependency.internal.OsrsHiscoresClient;
import com.osrs.goals.domainlogic.StatsService;
import com.osrs.goals.domainlogic.internal.DefaultStatsService;

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
