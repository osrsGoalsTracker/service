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
     * Allows injection of mock services in tests.
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

        if (input == null) {
            log.error("Request cannot be null");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HTTP_BAD_REQUEST)
                    .withBody("Request cannot be null");
        }

        Map<String, String> pathParameters = input.getPathParameters();
        if (pathParameters == null) {
            log.error("Path parameters cannot be null");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HTTP_BAD_REQUEST)
                    .withBody("Path parameters cannot be null");
        }

        String userId = pathParameters.get("userId");
        if (userId == null || userId.trim().isEmpty()) {
            log.error("User ID cannot be null or empty");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HTTP_BAD_REQUEST)
                    .withBody("User ID cannot be null or empty");
        }

        userId = userId.trim();
        List<PlayerEntity> players = playerService.getPlayersForUser(userId);
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
}