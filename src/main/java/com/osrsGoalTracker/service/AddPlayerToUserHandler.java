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
     * 
     * @param userService The user service to use for adding a player to a user.
     */
    @Inject
    public AddPlayerToUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Received request to add player to user");
        try {
            UserPlayerPair userPlayerPair = parseAndValidateInput(input);
            AddPlayerToUserResponse response = executeRequest(userPlayerPair);
            return createSuccessResponse(response);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage());
        } catch (Exception e) {
            log.error("Error processing request", e);
            return buildErrorResponse("Error processing request: " + e.getMessage());
        }
    }

    private record UserPlayerPair(String userId, String playerName) {
    }

    private UserPlayerPair parseAndValidateInput(APIGatewayProxyRequestEvent input) {
        if (input == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters == null) {
            throw new IllegalArgumentException("Path parameters cannot be null");
        }

        String userId = pathParameters.get("userId");
        String playerName = pathParameters.get("name");

        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }

        return new UserPlayerPair(userId.trim(), playerName.trim());
    }

    private AddPlayerToUserResponse executeRequest(UserPlayerPair userPlayerPair) {
        log.info("Adding player {} to user {}", userPlayerPair.playerName(), userPlayerPair.userId());
        userService.addPlayerToUser(userPlayerPair.userId(), userPlayerPair.playerName());
        return AddPlayerToUserResponse.builder()
                .userId(userPlayerPair.userId())
                .playerName(userPlayerPair.playerName())
                .build();
    }

    private APIGatewayProxyResponseEvent createSuccessResponse(AddPlayerToUserResponse response) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_OK)
                .withBody(JsonUtils.toJson(response));
    }

    private APIGatewayProxyResponseEvent buildErrorResponse(String message) {
        log.error(message);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_BAD_REQUEST)
                .withBody(String.format("{\"message\":\"%s\"}", message));
    }
}