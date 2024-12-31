package com.osrs.goals.service;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrs.goals.domainlogic.StatsService;
import com.osrs.goals.modules.StatsModule;
import com.osrs.goals.service.pojo.sao.PlayerStatsResponse;

import lombok.extern.log4j.Log4j2;

/**
 * AWS Lambda handler for retrieving player statistics from OSRS hiscores.
 * This class serves as the entry point for the Lambda function and coordinates
 * the request processing through the application layers.
 */
@Log4j2
public class GetPlayerStatsHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_SERVER_ERROR = 500;

    private final StatsService statsService;

    /**
     * Constructs a new GetPlayerStatsHandler.
     * Initializes the dependency injection container and retrieves required services.
     */
    public GetPlayerStatsHandler() {
        Injector injector = Guice.createInjector(new StatsModule());
        this.statsService = injector.getInstance(StatsService.class);
    }

    /**
     * Constructor for testing purposes.
     * Allows injection of mock services in tests.
     *
     * @param statsService The service for retrieving player statistics
     */
    GetPlayerStatsHandler(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * Handles the Lambda request to fetch player statistics.
     *
     * @param input The API Gateway request event
     * @param context The Lambda execution context
     * @return API Gateway response containing the player's statistics
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            final APIGatewayProxyRequestEvent input,
            final Context context) {
        try {
            if (input == null) {
                return createErrorResponse(HTTP_BAD_REQUEST, "Request cannot be null");
            }

            Map<String, String> pathParameters = input.getPathParameters();
            if (pathParameters == null) {
                return createErrorResponse(HTTP_BAD_REQUEST, "Path parameters cannot be null");
            }

            String username = pathParameters.get("rsn");
            if (username == null || username.trim().isEmpty()) {
                return createErrorResponse(HTTP_BAD_REQUEST, "Username cannot be null or empty");
            }

            username = username.trim();
            log.info("Received request to get player stats for username: {}", username);
            PlayerStatsResponse response = statsService.getPlayerStats(username);

            APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
            apiResponse.setStatusCode(HTTP_OK);
            apiResponse.setBody(OBJECT_MAPPER.writeValueAsString(response));
            return apiResponse;
        } catch (Exception e) {
            log.error("Error processing request", e);
            return createErrorResponse(HTTP_SERVER_ERROR, "Error processing request: " + e.getMessage());
        }
    }

    private APIGatewayProxyResponseEvent createErrorResponse(int statusCode, String message) {
        log.error(message);
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(statusCode);
        response.setBody(String.format("{\"message\":\"%s\"}", message));
        return response;
    }
}
