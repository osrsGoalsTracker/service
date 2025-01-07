package com.osrsGoalTracker.service;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.inject.Inject;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.service.pojo.sao.AddPlayerToUserResponse;
import com.osrsGoalTracker.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Lambda handler for adding a RuneScape player to a user's account.
 */
@Slf4j
public class AddPlayerToUserHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;

    private final UserService userService;

    /**
     * Constructor for AddPlayerToUserHandler.
     * @param userService The user service to use for adding a player to a user.
     */
    @Inject
    public AddPlayerToUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Received request to add player to user");

        if (input == null) {
            log.error("Request cannot be null");
            return buildErrorResponse("Request cannot be null");
        }

        Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters == null) {
            log.error("Path parameters cannot be null");
            return buildErrorResponse("Path parameters cannot be null");
        }

        String userId = pathParameters.get("userId");
        String playerName = pathParameters.get("name");

        if (userId == null || userId.trim().isEmpty()) {
            log.error("User ID cannot be null or empty");
            return buildErrorResponse("User ID cannot be null or empty");
        }

        if (playerName == null || playerName.trim().isEmpty()) {
            log.error("Player name cannot be null or empty");
            return buildErrorResponse("Player name cannot be null or empty");
        }

        userId = userId.trim();
        playerName = playerName.trim();

        userService.addPlayerToUser(userId, playerName);

        AddPlayerToUserResponse response = AddPlayerToUserResponse.builder()
                .userId(userId)
                .playerName(playerName)
                .build();

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_OK)
                .withBody(JsonUtils.toJson(response));
    }

    private APIGatewayProxyResponseEvent buildErrorResponse(String message) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_BAD_REQUEST)
                .withBody(String.format("{\"message\":\"%s\"}", message));
    }
}