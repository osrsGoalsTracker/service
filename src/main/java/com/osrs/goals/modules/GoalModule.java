package com.osrs.goals.modules;

import com.google.inject.AbstractModule;
import com.osrs.goals.data.GoalDataService;
import com.osrs.goals.data.internal.DefaultGoalDataService;
import com.osrs.goals.domainlogic.GoalService;
import com.osrs.goals.domainlogic.internal.DefaultGoalService;
import com.osrs.goals.persistence.GoalRepository;
import com.osrs.goals.persistence.internal.DefaultGoalRepository;

/**
 * Guice module for configuring goal-related dependencies.
 * This module binds all the necessary services and repositories
 * for managing goals.
 */
public class GoalModule extends AbstractModule {
    @Override
    protected void configure() {
        // Install database module
        ParameterStoreConfig parameterStoreConfig = new ParameterStoreConfig();
        install(new DsqlModule(parameterStoreConfig));

        // Bind domain logic layer
        bind(GoalService.class).to(DefaultGoalService.class);

        // Bind data layer
        bind(GoalDataService.class).to(DefaultGoalDataService.class);

        // Bind persistence layer
        bind(GoalRepository.class).to(DefaultGoalRepository.class);
    }
}
