package com.osrsGoalTracker.goal.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.osrsGoalTracker.goal.model.Goal;
import com.osrsGoalTracker.goal.service.GoalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateGoalFromGoalCreationRequestEventHandlerTest {
    @Mock
    private GoalService goalService;
    @Mock
    private Context context;

    private CreateGoalFromGoalCreationRequestEventHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreateGoalFromGoalCreationRequestEventHandler(goalService);
    }

    @Test
    void handleRequest_ValidEvent_CreatesGoal() {
        // Given
        String userId = "testUser";
        Instant now = Instant.now();
        Instant targetDate = now.plus(30, ChronoUnit.DAYS);
        Map<String, Object> detail = new HashMap<>();
        detail.put("userId", userId);
        detail.put("characterName", "testChar");
        detail.put("targetAttribute", "ATTACK");
        detail.put("targetType", "LEVEL");
        detail.put("targetValue", 99L);
        detail.put("currentValue", 1L);
        detail.put("targetDate", targetDate.toString());
        detail.put("notificationChannelType", "DISCORD");
        detail.put("frequency", "DAILY");

        ScheduledEvent event = new ScheduledEvent();
        event.setDetail(detail);

        Goal expectedGoal = Goal.builder()
                .userId(userId)
                .characterName("testChar")
                .targetAttribute("ATTACK")
                .targetType("LEVEL")
                .targetValue(99L)
                .targetDate(targetDate)
                .notificationChannelType("DISCORD")
                .frequency("DAILY")
                .build();

        when(goalService.createGoal(any(Goal.class), anyLong())).thenReturn(expectedGoal);

        // When
        Goal actualGoal = handler.handleRequest(event, context);

        // Then
        verify(goalService).createGoal(any(Goal.class), anyLong());
        assertEquals(expectedGoal, actualGoal);
    }

    @Test
    void handleRequest_InvalidEvent_ThrowsException() {
        // Given
        ScheduledEvent event = new ScheduledEvent();
        event.setDetail(new HashMap<>());

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> handler.handleRequest(event, context));
    }

    @Test
    void handleRequest_NullEvent_ThrowsException() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> handler.handleRequest(null, context));
    }
}