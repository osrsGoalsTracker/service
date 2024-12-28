package com.osrs.goals.lambda;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.persistence.PlayerRepositoryImpl.DynamoDbPlayerRepository;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the SetPlayerHandler Lambda function.
 */
public class SetPlayerHandlerTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @Mock
    private Context context;

    private SetPlayerHandler handler;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(PutItemResponse.builder().build());
        
        DynamoDbPlayerRepository playerRepository = new DynamoDbPlayerRepository(dynamoDbClient);
        PlayerService playerService = new PlayerService(playerRepository);
        handler = new SetPlayerHandler(playerService);
    }

    /**
     * Tests successful saving of a player.
     */
    @Test
    void shouldSavePlayerSuccessfully() {
        // Arrange
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("rsn", "test player");
        request.setPathParameters(pathParams);

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should successfully save player");
        assertEquals("Player saved successfully", response.getBody());
    }

    /**
     * Tests handling of a request with missing RSN parameter.
     */
    @Test
    void shouldReturn400WhenRsnIsMissing() {
        // Arrange
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return bad request when RSN is missing");
        assertEquals("RSN is required in the path", response.getBody());
    }

    /**
     * Tests handling of a request with empty RSN parameter.
     */
    @Test
    void shouldReturn400WhenRsnIsEmpty() {
        // Arrange
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("rsn", "  ");
        request.setPathParameters(pathParams);

        // Act
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return bad request when RSN is empty");
        assertEquals("RSN cannot be empty", response.getBody());
    }
}
