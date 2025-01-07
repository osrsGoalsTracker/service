package com.osrsGoalTracker.modules;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.dao.goalTracker.module.GoalTrackerDaoModule;
import com.osrsGoalTracker.data.PlayerDataService;
import com.osrsGoalTracker.data.internal.DefaultPlayerDataService;
import com.osrsGoalTracker.domainlogic.PlayerService;
import com.osrsGoalTracker.domainlogic.internal.DefaultPlayerService;

/**
 * Guice module for player-related dependency injection.
 */
public class PlayerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GoalTrackerDaoModule());

        bind(PlayerService.class).to(DefaultPlayerService.class);
        bind(PlayerDataService.class).to(DefaultPlayerDataService.class);
    }
}