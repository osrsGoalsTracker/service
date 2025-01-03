package com.osrs.goals.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrs.goals.domainlogic.StatsService;
import com.osrs.goals.service.pojo.sao.PlayerStatsResponse;

@ExtendWith(MockitoExtension.class)
class GetPlayerStatsHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;

    @Mock
    private StatsService statsService;

    @InjectMocks
    private GetPlayerStatsHandler handler;

    @Test
    void handleRequestShouldReturnPlayerStats() throws Exception {
        // Given
        String username = "testUser";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", username);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        PlayerStatsResponse serviceResponse = PlayerStatsResponse.builder()
                .username(username)
                .build();

        when(statsService.getPlayerStats(username)).thenReturn(serviceResponse);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());
        PlayerStatsResponse responseBody = OBJECT_MAPPER.readValue(result.getBody(), PlayerStatsResponse.class);
        assertEquals(username, responseBody.getUsername());
    }

    @Test
    void handleRequestShouldTrimUsername() throws Exception {
        // Given
        String username = "  testUser  ";
        String trimmedUsername = "testUser";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", username);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        PlayerStatsResponse serviceResponse = PlayerStatsResponse.builder()
                .username(trimmedUsername)
                .build();

        when(statsService.getPlayerStats(trimmedUsername)).thenReturn(serviceResponse);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());
        PlayerStatsResponse responseBody = OBJECT_MAPPER.readValue(result.getBody(), PlayerStatsResponse.class);
        assertEquals(trimmedUsername, responseBody.getUsername());
    }

    @Test
    void handleRequestShouldReturn400WhenRequestIsNull() {
        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(null, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Request cannot be null\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenPathParametersAreNull() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Path parameters cannot be null\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenUsernameIsNull() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", null);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Username cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenUsernameIsEmpty() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", "");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Username cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenUsernameIsBlank() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", "   ");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Username cannot be null or empty\"}", result.getBody());
    }
}
