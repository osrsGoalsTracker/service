package com.osrsGoalTracker.character.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrsGoalTracker.character.repository.CharacterRepository;
import com.osrsGoalTracker.character.repository.impl.CharacterRepositoryImpl;
import com.osrsGoalTracker.character.service.CharacterService;
import com.osrsGoalTracker.character.service.impl.CharacterServiceImpl;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;

import org.junit.jupiter.api.Test;

class CharacterModuleTest {

    private static class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(GoalTrackerDao.class).toInstance(mock(GoalTrackerDao.class));
        }
    }

    @Test
    void testCharacterModuleBindings() {
        // Given
        Injector injector = Guice.createInjector(new CharacterModule(), new TestModule());

        // When
        CharacterService characterService = injector.getInstance(CharacterService.class);
        CharacterRepository characterRepository = injector.getInstance(CharacterRepository.class);

        // Then
        assertNotNull(characterService, "CharacterService should be bound");
        assertNotNull(characterRepository, "CharacterRepository should be bound");
        assertTrue(characterService instanceof CharacterServiceImpl,
                "CharacterService should be bound to CharacterServiceImpl");
        assertTrue(characterRepository instanceof CharacterRepositoryImpl,
                "CharacterRepository should be bound to CharacterRepositoryImpl");
    }
}