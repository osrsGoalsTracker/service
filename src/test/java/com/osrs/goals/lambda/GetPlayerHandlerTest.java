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
import static org.mockito.Mockito.when;

/**
 * Unit tests for the GetPlayerHandler Lambda function.
 */
public class GetPlayerHandlerTest {
    @Mock
    private PlayerService playerService;

    @Mock
    private Context context;

    private GetPlayerHandler handler;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetPlayerHandler(playerService);
    }

    /**
     * Tests successful retrieval of an existing player.
     */
    @Test
    void shouldReturnSuccessResponseWhenPlayerExists() {
        // Arrange
        String rsn = "testPlayer";
        Player player = Player.builder()
                .rsn(rsn)
                .lastUpdated("2023-12-05T00:00:00Z")
                .build();

        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", rsn);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        when(playerService.getPlayer(rsn)).thenReturn(Optional.of(player));

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedJson = "{\"rsn\":\"testPlayer\",\"lastUpdated\":\"2023-12-05T00:00:00Z\"}";
        assertEquals(expectedJson, response.getBody());
    }

    /**
     * Tests handling of a request for a non-existent player.
     */
    @Test
    void shouldReturn404WhenPlayerDoesNotExist() {
        // Arrange
        String rsn = "nonExistentPlayer";
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("rsn", rsn);

        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        when(playerService.getPlayer(rsn)).thenReturn(Optional.empty());

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Player not found", response.getBody());
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
}
