package com.osrs.goals.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrs.goals.external.OsrsStatsService;
import com.osrs_hiscores_fetcher.api.models.Activity;
import com.osrs_hiscores_fetcher.api.models.OsrsPlayer;
import com.osrs_hiscores_fetcher.api.models.Skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the GetPlayerOsrsStatsHandler Lambda function.
 */
public class GetPlayerOsrsStatsHandlerTest {
    @Mock
    private OsrsStatsService osrsStatsService;

    @Mock
    private Context context;

    private GetPlayerOsrsStatsHandler handler;
    private ObjectMapper objectMapper;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetPlayerOsrsStatsHandler(osrsStatsService);
        objectMapper = new ObjectMapper();
    }

    /**
     * Tests successful retrieval of player stats.
     */
    @Test
    void shouldReturnPlayerStatsSuccessfully() throws Exception {
        // Arrange
        String rsn = "test player";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", rsn);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        List<Skill> skills = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();
        OsrsPlayer player = new OsrsPlayer(rsn, skills, activities);
        when(osrsStatsService.getPlayerStats(rsn)).thenReturn(Optional.of(player));

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(objectMapper.writeValueAsString(player), response.getBody());
    }

    /**
     * Tests handling of a request with missing RSN parameter.
     */
    @Test
    void shouldReturn400WhenRsnIsMissing() {
        // Arrange
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(new HashMap<>());

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("RSN is required", response.getBody());
    }

    /**
     * Tests handling of a request with empty RSN parameter.
     */
    @Test
    void shouldReturn400WhenRsnIsEmpty() {
        // Arrange
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", "  ");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("RSN is required", response.getBody());
    }

    /**
     * Tests handling of a request for a non-existent player.
     */
    @Test
    void shouldReturn404WhenPlayerNotFound() {
        // Arrange
        String rsn = "nonexistent player";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", rsn);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        when(osrsStatsService.getPlayerStats(rsn)).thenReturn(Optional.empty());

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Player stats not found", response.getBody());
    }

    /**
     * Tests handling of a request that results in an error.
     */
    @Test
    void shouldReturn502WhenErrorOccurs() {
        // Arrange
        String rsn = "error player";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", rsn);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        when(osrsStatsService.getPlayerStats(anyString()))
                .thenThrow(new RuntimeException("Test error"));

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals("Test error", response.getBody());
    }
} 
