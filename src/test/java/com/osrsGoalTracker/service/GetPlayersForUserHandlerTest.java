package com.osrsGoalTracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.domainlogic.PlayerService;
import com.osrsGoalTracker.service.pojo.sao.GetPlayersForUserResponse;
import com.osrsGoalTracker.utils.JsonUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetPlayersForUserHandlerTest {
    private PlayerService playerService;
    private GetPlayersForUserHandler handler;
    private Context context;

    @BeforeEach
    void setUp() {
        playerService = mock(PlayerService.class);
        handler = new GetPlayersForUserHandler(playerService);
        context = mock(Context.class);
    }

    @Test
    void handleRequestShouldReturnPlayersForUser() {
        String userId = "testUser";
        List<PlayerEntity> players = Arrays.asList(
                createPlayerEntity("player1"),
                createPlayerEntity("player2"));
        when(playerService.getPlayersForUser(userId)).thenReturn(players);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", userId);
        request.setPathParameters(pathParameters);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertEquals(200, response.getStatusCode());
        GetPlayersForUserResponse expectedResponse = GetPlayersForUserResponse.builder()
                .userId(userId)
                .playerNames(Arrays.asList("player1", "player2"))
                .build();
        assertEquals(JsonUtils.toJson(expectedResponse), response.getBody());
        verify(playerService).getPlayersForUser(userId);
    }

    @Test
    void handleRequestShouldReturnEmptyListWhenNoPlayers() {
        String userId = "testUser";
        when(playerService.getPlayersForUser(userId)).thenReturn(Collections.emptyList());

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", userId);
        request.setPathParameters(pathParameters);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertEquals(200, response.getStatusCode());
        GetPlayersForUserResponse expectedResponse = GetPlayersForUserResponse.builder()
                .userId(userId)
                .playerNames(Collections.emptyList())
                .build();
        assertEquals(JsonUtils.toJson(expectedResponse), response.getBody());
        verify(playerService).getPlayersForUser(userId);
    }

    @Test
    void handleRequestShouldReturn400WhenRequestIsNull() {
        APIGatewayProxyResponseEvent response = handler.handleRequest(null, context);

        assertEquals(400, response.getStatusCode());
        assertEquals("{\"message\":\"Request cannot be null\"}", response.getBody());
        verify(playerService, never()).getPlayersForUser(anyString());
    }

    @Test
    void handleRequestShouldReturn400WhenPathParametersAreNull() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setPathParameters(null);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertEquals(400, response.getStatusCode());
        assertEquals("{\"message\":\"Path parameters cannot be null\"}", response.getBody());
        verify(playerService, never()).getPlayersForUser(anyString());
    }

    @Test
    void handleRequestShouldReturn400WhenUserIdIsNull() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", null);
        request.setPathParameters(pathParameters);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertEquals(400, response.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", response.getBody());
        verify(playerService, never()).getPlayersForUser(anyString());
    }

    @Test
    void handleRequestShouldReturn400WhenUserIdIsEmpty() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "");
        request.setPathParameters(pathParameters);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertEquals(400, response.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", response.getBody());
        verify(playerService, never()).getPlayersForUser(anyString());
    }

    @Test
    void handleRequestShouldReturn400WhenUserIdIsBlank() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", "   ");
        request.setPathParameters(pathParameters);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertEquals(400, response.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", response.getBody());
        verify(playerService, never()).getPlayersForUser(anyString());
    }

    private PlayerEntity createPlayerEntity(String name) {
        return PlayerEntity.builder()
                .name(name)
                .build();
    }
}