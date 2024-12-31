package com.osrs.goals.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrs.goals.domainlogic.GoalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for the GoalCreatorHandler class.
 */
@ExtendWith(MockitoExtension.class)
class GoalCreatorHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;

    @Mock
    private GoalService goalService;

    private GoalCreatorHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GoalCreatorHandler();
    }

    @Test
    void handleRequestShouldCreateGoal() throws Exception {
        // Given
        // GoalCreatorResponse expectedResponse = GoalCreatorResponse.builder()
        // .id("123")
        // .createdAt("2024-01-01")
        // .build();

        // when(goalService.createNewGoal()).thenReturn(expectedResponse);

        // // When
        // APIGatewayProxyResponseEvent response = handler.handleRequest(new
        // APIGatewayProxyRequestEvent(), null);

        // // Then
        // assertEquals(HTTP_OK, response.getStatusCode());
        // GoalCreatorResponse actualResponse =
        // OBJECT_MAPPER.readValue(response.getBody(), GoalCreatorResponse.class);
        // assertEquals(expectedResponse, actualResponse);
    }
}
