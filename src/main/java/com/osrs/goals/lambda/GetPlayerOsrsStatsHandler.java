package com.osrs.goals.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrs.goals.external.OsrsStatsService;
import com.osrs.goals.modules.PlayerModule;

import lombok.extern.log4j.Log4j2;

/**
 * Lambda handler for retrieving OSRS player statistics.
 */
@Log4j2
public class GetPlayerOsrsStatsHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final OsrsStatsService osrsStatsService;
    private final ObjectMapper objectMapper;

    /**
     * Default constructor used by AWS Lambda.
     */
    public GetPlayerOsrsStatsHandler() {
        Injector injector = Guice.createInjector(new PlayerModule());
        this.osrsStatsService = injector.getInstance(OsrsStatsService.class);
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Constructor for testing.
     *
     * @param osrsStatsService The OSRS stats service to use
     */
    GetPlayerOsrsStatsHandler(OsrsStatsService osrsStatsService) {
        this.osrsStatsService = osrsStatsService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            String rsn = input.getPathParameters().get("rsn");
            if (rsn == null || rsn.trim().isEmpty()) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(HttpStatus.BAD_REQUEST)
                        .withBody("RSN is required");
            }

            var stats = osrsStatsService.getPlayerStats(rsn);
            if (stats.isPresent()) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(HttpStatus.OK)
                        .withBody(objectMapper.writeValueAsString(stats.get()));
            }

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.NOT_FOUND)
                    .withBody("Player stats not found");

        } catch (Exception e) {
            log.error("Error processing request", e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.BAD_GATEWAY)
                    .withBody(e.getMessage());
        }
    }
} 
