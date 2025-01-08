package com.osrsGoalTracker.modules;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.repository.CharacterRepository;
import com.osrsGoalTracker.repository.impl.CharacterRepositoryImpl;
import com.osrsGoalTracker.service.CharacterService;
import com.osrsGoalTracker.service.impl.CharacterServiceImpl;

/**
 * Guice module for character-related bindings.
 */
public class PlayerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CharacterRepository.class).to(CharacterRepositoryImpl.class);
        bind(CharacterService.class).to(CharacterServiceImpl.class);
    }
}