package com.osrsGoalTracker.modules;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.external.HiscoresClient;
import com.osrsGoalTracker.external.impl.JagexHiscoresClientImpl;
import com.osrsGoalTracker.service.HiscoresService;
import com.osrsGoalTracker.service.impl.HiscoresServiceImpl;

/**
 * Guice module for hiscores-related bindings.
 */
public class HiscoresModule extends AbstractModule {
    /**
     * Configures the bindings for stats-related services and dependencies.
     * This includes the service layer and external layer components.
     */
    @Override
    protected void configure() {
        bind(HiscoresClient.class).to(JagexHiscoresClientImpl.class);
        bind(HiscoresService.class).to(HiscoresServiceImpl.class);
    }
}
