package com.osrsGoalTracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.service.pojo.sao.AddPlayerToUserResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddPlayerToUserHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;

    @Mock
    private UserService userService;

    @InjectMocks
    private AddPlayerToUserHandler handler;

    @Test
    void handleRequestShouldAddPlayerToUser() throws Exception {
        // Given
        String userId = "testUserId";
        String playerName = "testPlayer";

        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", userId);
        pathParameters.put("name", playerName);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        PlayerEntity player = PlayerEntity.builder()
                .userId(userId)
                .name(playerName)
                .build();

        when(userService.addPlayerToUser(userId, playerName)).thenReturn(player);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());

        AddPlayerToUserResponse expectedResponse = AddPlayerToUserResponse.builder()
                .userId(userId)
                .playerName(playerName)
                .build();

        String expectedJson = OBJECT_MAPPER.writeValueAsString(expectedResponse);
        assertEquals(expectedJson, result.getBody());
    }

    @Test
    void handleRequestShouldTrimUserIdAndPlayerName() throws Exception {
        // Given
        String userId = "  testUserId  ";
        String playerName = "  testPlayer  ";
        String trimmedUserId = "testUserId";
        String trimmedPlayerName = "testPlayer";

        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", userId);
        pathParameters.put("name", playerName);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        PlayerEntity player = PlayerEntity.builder()
                .userId(trimmedUserId)
                .name(trimmedPlayerName)
                .build();

        when(userService.addPlayerToUser(trimmedUserId, trimmedPlayerName)).thenReturn(player);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());

        AddPlayerToUserResponse expectedResponse = AddPlayerToUserResponse.builder()
                .userId(trimmedUserId)
                .playerName(trimmedPlayerName)
                .build();

        String expectedJson = OBJECT_MAPPER.writeValueAsString(expectedResponse);
        assertEquals(expectedJson, result.getBody());
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
    void handleRequestShouldReturn400WhenUserIdIsNull() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", null);
        pathParameters.put("name", "testPlayer");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenUserIdIsEmpty() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "");
        pathParameters.put("name", "testPlayer");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenUserIdIsBlank() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "   ");
        pathParameters.put("name", "testPlayer");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenPlayerNameIsNull() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "testUserId");
        pathParameters.put("name", null);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Player name cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenPlayerNameIsEmpty() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "testUserId");
        pathParameters.put("name", "");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Player name cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenPlayerNameIsBlank() {
        // Given
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "testUserId");
        pathParameters.put("name", "   ");

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Player name cannot be null or empty\"}", result.getBody());
    }
}