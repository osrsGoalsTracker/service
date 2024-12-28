package com.osrs.goals.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.modules.PlayerModule;

import lombok.extern.log4j.Log4j2;

/**
 * Lambda handler for retrieving player data.
 */
@Log4j2
public class GetPlayerHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final PlayerService playerService;
    private final ObjectMapper objectMapper;

    /**
     * Default constructor used by AWS Lambda.
     */
    public GetPlayerHandler() {
        Injector injector = Guice.createInjector(new PlayerModule());
        this.playerService = injector.getInstance(PlayerService.class);
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Constructor for testing.
     *
     * @param playerService The player service to use
     */
    GetPlayerHandler(PlayerService playerService) {
        this.playerService = playerService;
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

            var player = playerService.getPlayer(rsn);
            if (player.isPresent()) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(HttpStatus.OK)
                        .withBody(objectMapper.writeValueAsString(player.get()));
            }

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.NOT_FOUND)
                    .withBody("Player not found");

        } catch (Exception e) {
            log.error("Error processing request", e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.BAD_GATEWAY)
                    .withBody(e.getMessage());
        }
    }
}
