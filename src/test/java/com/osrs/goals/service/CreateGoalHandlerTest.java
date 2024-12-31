package com.osrs.goals.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrs.goals.domainlogic.GoalService;
import com.osrs.goals.service.pojo.sao.CreateGoalResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for the CreateGoalHandler class.
 */
@ExtendWith(MockitoExtension.class)
class CreateGoalHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;

    @Mock
    private Context context;

    private CreateGoalHandler handler;
    private GoalService goalService;

    @BeforeEach
    void setUp() {
        goalService = mock(GoalService.class);
        handler = new CreateGoalHandler(goalService);
    }

    @Test
    public void testHandleRequest() {
        // Test implementation
    }

    @Test
    void handleRequestShouldCreateGoal() throws Exception {
        // Given
        CreateGoalResponse expectedResponse = CreateGoalResponse.builder()
                .id("test-id")
                .createdAt("2024-01-01T00:00:00Z")
                .updatedAt("2024-01-01T00:00:00Z")
                .build();
        when(goalService.createGoal()).thenReturn(expectedResponse);

        // When
        APIGatewayProxyResponseEvent response = handler.handleRequest(null, context);

        // Then
        assertNotNull(response);
        assertEquals(HTTP_OK, response.getStatusCode());
        CreateGoalResponse actualResponse = OBJECT_MAPPER.readValue(response.getBody(), CreateGoalResponse.class);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getCreatedAt(), actualResponse.getCreatedAt());
        assertEquals(expectedResponse.getUpdatedAt(), actualResponse.getUpdatedAt());
    }
}
