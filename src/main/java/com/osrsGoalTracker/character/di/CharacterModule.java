package com.osrsGoalTracker.character.di;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.dao.goalTracker.module.GoalTrackerDaoModule;
import com.osrsGoalTracker.character.repository.CharacterRepository;
import com.osrsGoalTracker.character.repository.impl.CharacterRepositoryImpl;
import com.osrsGoalTracker.character.service.CharacterService;
import com.osrsGoalTracker.character.service.impl.CharacterServiceImpl;

/**
 * Guice module for character-related bindings.
 */
public class CharacterModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GoalTrackerDaoModule());
        bind(CharacterRepository.class).to(CharacterRepositoryImpl.class);
        bind(CharacterService.class).to(CharacterServiceImpl.class);
    }
}