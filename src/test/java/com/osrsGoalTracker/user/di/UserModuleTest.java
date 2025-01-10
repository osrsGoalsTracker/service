package com.osrsGoalTracker.user.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.user.repository.UserRepository;
import com.osrsGoalTracker.user.repository.impl.UserRepositoryImpl;
import com.osrsGoalTracker.user.service.UserService;
import com.osrsGoalTracker.user.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserModuleTest {

    private GoalTrackerDao mockGoalTrackerDao;
    private Injector injector;

    @BeforeEach
    void setUp() {
        mockGoalTrackerDao = mock(GoalTrackerDao.class);

        AbstractModule testModule = new AbstractModule() {
            @Override
            protected void configure() {
                bind(GoalTrackerDao.class).toInstance(mockGoalTrackerDao);
            }
        };

        injector = Guice.createInjector(
                Modules.override(new UserModule())
                        .with(testModule));
    }

    @Test
    void testUserModuleBindings() {
        // When
        UserService userService = injector.getInstance(UserService.class);
        UserRepository userRepository = injector.getInstance(UserRepository.class);

        // Then
        assertNotNull(userService, "UserService should be bound");
        assertNotNull(userRepository, "UserRepository should be bound");
        assertTrue(userService instanceof UserServiceImpl,
                "UserService should be bound to UserServiceImpl");
        assertTrue(userRepository instanceof UserRepositoryImpl,
                "UserRepository should be bound to UserRepositoryImpl");
    }
}