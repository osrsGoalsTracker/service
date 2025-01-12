package com.osrsGoalTracker.goal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.osrsGoalTracker.goal.model.Goal;
import com.osrsGoalTracker.goal.repository.GoalRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    private GoalServiceImpl goalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        goalService = new GoalServiceImpl(goalRepository);
    }

    @Test
    void createGoal_ValidGoal_ReturnsCreatedGoal() {
        // Given
        String userId = "user123";
        String characterName = "testChar";
        Instant now = Instant.now();
        Instant targetDate = now.plus(30, ChronoUnit.DAYS);

        Goal goal = Goal.builder()
                .userId(userId)
                .characterName(characterName)
                .targetAttribute("ATTACK")
                .targetType("LEVEL")
                .targetValue(99)
                .targetDate(targetDate)
                .notificationChannelType("EMAIL")
                .frequency("DAILY")
                .build();

        long currentProgress = 50;

        when(goalRepository.createGoal(any(Goal.class), any(Long.class))).thenReturn(goal);

        // When
        Goal result = goalService.createGoal(goal, currentProgress);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(characterName, result.getCharacterName());
        assertEquals("ATTACK", result.getTargetAttribute());
        assertEquals("LEVEL", result.getTargetType());
        assertEquals(99, result.getTargetValue());
        assertEquals(targetDate, result.getTargetDate());
        assertEquals("EMAIL", result.getNotificationChannelType());
        assertEquals("DAILY", result.getFrequency());

        verify(goalRepository).createGoal(any(Goal.class), any(Long.class));
    }

    @Test
    void createGoal_NullGoal_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> goalService.createGoal(null, 0));
    }

    @Test
    void createGoal_NullUserId_ThrowsException() {
        Instant now = Instant.now();
        Goal goal = Goal.builder()
                .userId(null)
                .characterName("testChar")
                .targetAttribute("ATTACK")
                .targetType("LEVEL")
                .targetValue(99)
                .targetDate(now.plus(30, ChronoUnit.DAYS))
                .notificationChannelType("EMAIL")
                .frequency("DAILY")
                .build();

        assertThrows(IllegalArgumentException.class, () -> goalService.createGoal(goal, 0));
    }

    @Test
    void createGoal_NullCharacterName_ThrowsException() {
        Instant now = Instant.now();
        Goal goal = Goal.builder()
                .userId("user123")
                .characterName(null)
                .targetAttribute("ATTACK")
                .targetType("LEVEL")
                .targetValue(99)
                .targetDate(now.plus(30, ChronoUnit.DAYS))
                .notificationChannelType("EMAIL")
                .frequency("DAILY")
                .build();

        assertThrows(IllegalArgumentException.class, () -> goalService.createGoal(goal, 0));
    }
}