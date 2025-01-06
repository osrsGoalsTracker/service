package com.osrsGoalTracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrsGoalTracker.data.pojo.domain.User;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.service.pojo.sao.GetUserResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mock
    private UserService userService;

    @InjectMocks
    private GetUserHandler handler;

    @Test
    void handleRequestShouldReturnUser() throws Exception {
        // Given
        String userId = "testUserId";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", userId);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId(userId)
                .email("test@example.com")
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(userService.getUser(userId)).thenReturn(user);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());

        GetUserResponse expectedResponse = GetUserResponse.builder()
                .userId(userId)
                .email("test@example.com")
                .createdAt(DATE_TIME_FORMATTER.format(now))
                .updatedAt(DATE_TIME_FORMATTER.format(now))
                .build();

        String expectedJson = OBJECT_MAPPER.writeValueAsString(expectedResponse);
        assertEquals(expectedJson, result.getBody());
    }

    @Test
    void handleRequestShouldTrimUserId() throws Exception {
        // Given
        String userId = "  testUserId  ";
        String trimmedUserId = "testUserId";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("userId", userId);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId(trimmedUserId)
                .email("test@example.com")
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(userService.getUser(trimmedUserId)).thenReturn(user);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());

        GetUserResponse expectedResponse = GetUserResponse.builder()
                .userId(trimmedUserId)
                .email("test@example.com")
                .createdAt(DATE_TIME_FORMATTER.format(now))
                .updatedAt(DATE_TIME_FORMATTER.format(now))
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

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"User ID cannot be null or empty\"}", result.getBody());
    }
}