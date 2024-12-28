package com.osrs.goals.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the SetPlayerHandler Lambda function.
 */
public class SetPlayerHandlerTest {
    @Mock
    private PlayerService playerService;

    @Mock
    private Context context;

    private SetPlayerHandler handler;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new SetPlayerHandler(playerService);
    }

    /**
     * Tests successful player save.
     */
    @Test
    void shouldSavePlayerSuccessfully() {
        // Arrange
        String rsn = "test player";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", rsn);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        when(playerService.getPlayer(rsn)).thenReturn(Optional.empty());

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(playerService).savePlayer(any(Player.class));
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
        assertEquals("RSN is required in the path", response.getBody());
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
        assertEquals("RSN cannot be empty", response.getBody());
    }
}
