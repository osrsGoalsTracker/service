package com.osrsGoalTracker.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.domainlogic.PlayerService;
import com.osrsGoalTracker.modules.PlayerModule;
import com.osrsGoalTracker.service.pojo.sao.GetPlayersForUserResponse;
import com.osrsGoalTracker.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Lambda handler for retrieving all players associated with a user.
 */
@Slf4j
public class GetPlayersForUserHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;

    private final PlayerService playerService;

    /**
     * Default constructor for AWS Lambda.
     * This constructor is required by AWS Lambda to instantiate the handler.
     */
    public GetPlayersForUserHandler() {
        Injector injector = Guice.createInjector(new PlayerModule());
        this.playerService = injector.getInstance(PlayerService.class);
    }

    /**
     * Constructor for testing purposes.
     *
     * @param playerService The PlayerService instance to use for retrieving players
     */
    @Inject
    public GetPlayersForUserHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Received request to get players for user");
        try {
            String userId = parseAndValidateInput(input);
            List<PlayerEntity> players = executeRequest(userId);
            return createSuccessResponse(userId, players);
        } catch (IllegalArgumentException e) {
            return createErrorResponse(e.getMessage());
        }
    }

    private String parseAndValidateInput(APIGatewayProxyRequestEvent input) {
        if (input == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters == null) {
            throw new IllegalArgumentException("Path parameters cannot be null");
        }

        String userId = pathParameters.get("userId");
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        return userId.trim();
    }

    private List<PlayerEntity> executeRequest(String userId) {
        return playerService.getPlayersForUser(userId);
    }

    /**
     * Creates a success response with the list of players.
     *
     * @param userId  The user ID
     * @param players The list of player entities
     * @return The API Gateway response event
     */
    private APIGatewayProxyResponseEvent createSuccessResponse(String userId, List<PlayerEntity> players) {
        List<String> playerNames = players.stream()
                .map(PlayerEntity::getName)
                .collect(Collectors.toList());

        GetPlayersForUserResponse response = GetPlayersForUserResponse.builder()
                .userId(userId)
                .playerNames(playerNames)
                .build();

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_OK)
                .withBody(JsonUtils.toJson(response));
    }

    /**
     * Creates an error response with the given message.
     *
     * @param message The error message
     * @return The API Gateway response event
     */
    private APIGatewayProxyResponseEvent createErrorResponse(String message) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_BAD_REQUEST)
                .withBody(String.format("{\"message\":\"%s\"}", message));
    }
}