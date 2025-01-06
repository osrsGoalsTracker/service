package com.osrsGoalTracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osrsGoalTracker.data.pojo.domain.User;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.service.pojo.sao.CreateUserRequest;
import com.osrsGoalTracker.service.pojo.sao.GetUserResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUserHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateUserHandler handler;

    @Test
    void handleRequestShouldCreateUser() throws Exception {
        // Given
        String email = "test@example.com";
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.setEmail(email);

        String requestBody = OBJECT_MAPPER.writeValueAsString(createRequest);
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId("testUserId")
                .email(email)
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(userService.createUser(email)).thenReturn(user);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());

        GetUserResponse expectedResponse = GetUserResponse.builder()
                .userId("testUserId")
                .email(email)
                .createdAt(DATE_TIME_FORMATTER.format(now))
                .updatedAt(DATE_TIME_FORMATTER.format(now))
                .build();

        String expectedJson = OBJECT_MAPPER.writeValueAsString(expectedResponse);
        assertEquals(expectedJson, result.getBody());
    }

    @Test
    void handleRequestShouldTrimEmail() throws Exception {
        // Given
        String email = "  test@example.com  ";
        String trimmedEmail = "test@example.com";
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.setEmail(email);

        String requestBody = OBJECT_MAPPER.writeValueAsString(createRequest);
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId("testUserId")
                .email(trimmedEmail)
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(userService.createUser(trimmedEmail)).thenReturn(user);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_OK, result.getStatusCode());
        assertNotNull(result.getBody());

        GetUserResponse expectedResponse = GetUserResponse.builder()
                .userId("testUserId")
                .email(trimmedEmail)
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
    void handleRequestShouldReturn400WhenBodyIsNull() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(null);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Request body cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenBodyIsEmpty() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("");

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Request body cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenBodyIsBlank() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("   ");

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Request body cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenEmailIsNull() throws Exception {
        // Given
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.setEmail(null);

        String requestBody = OBJECT_MAPPER.writeValueAsString(createRequest);
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Email cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenEmailIsEmpty() throws Exception {
        // Given
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.setEmail("");

        String requestBody = OBJECT_MAPPER.writeValueAsString(createRequest);
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Email cannot be null or empty\"}", result.getBody());
    }

    @Test
    void handleRequestShouldReturn400WhenEmailIsBlank() throws Exception {
        // Given
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.setEmail("   ");

        String requestBody = OBJECT_MAPPER.writeValueAsString(createRequest);
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(requestBody);

        // When
        APIGatewayProxyResponseEvent result = handler.handleRequest(request, null);

        // Then
        assertEquals(HTTP_BAD_REQUEST, result.getStatusCode());
        assertEquals("{\"message\":\"Email cannot be null or empty\"}", result.getBody());
    }
}