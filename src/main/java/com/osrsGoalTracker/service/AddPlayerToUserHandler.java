package com.osrsGoalTracker.service;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.osrsGoalTracker.dao.goalTracker.entity.PlayerEntity;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.modules.UserModule;
import com.osrsGoalTracker.service.pojo.sao.AddPlayerToUserResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddPlayerToUserHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;

    private final UserService userService;

    /**
     * Default constructor for AWS Lambda.
     * This constructor is required by AWS Lambda to instantiate the handler.
     */
    public AddPlayerToUserHandler() {
        Injector injector = Guice.createInjector(new UserModule());
        this.userService = injector.getInstance(UserService.class);
    }

    /**
     * Constructor for testing purposes.
     * Allows injection of mock services in tests.
     *
     * @param userService The UserService instance to use for user operations
     */
    @Inject
    AddPlayerToUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Received request to add player to user");

        if (input == null) {
            return buildErrorResponse("Request cannot be null");
        }

        Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters == null) {
            return buildErrorResponse("Path parameters cannot be null");
        }

        String userId = pathParameters.get("userId");
        if (userId == null || userId.trim().isEmpty()) {
            return buildErrorResponse("User ID cannot be null or empty");
        }
        userId = userId.trim();

        String playerName = pathParameters.get("name");
        if (playerName == null || playerName.trim().isEmpty()) {
            return buildErrorResponse("Player name cannot be null or empty");
        }
        playerName = playerName.trim();

        try {
            PlayerEntity player = userService.addPlayerToUser(userId, playerName);
            AddPlayerToUserResponse response = AddPlayerToUserResponse.builder()
                    .userId(player.getUserId())
                    .playerName(player.getName())
                    .build();

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HTTP_OK)
                    .withBody(OBJECT_MAPPER.writeValueAsString(response));
        } catch (Exception e) {
            log.error("Error adding player to user", e);
            return buildErrorResponse("Error adding player to user: " + e.getMessage());
        }
    }

    private APIGatewayProxyResponseEvent buildErrorResponse(String message) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HTTP_BAD_REQUEST)
                .withBody(String.format("{\"message\":\"%s\"}", message));
    }
}