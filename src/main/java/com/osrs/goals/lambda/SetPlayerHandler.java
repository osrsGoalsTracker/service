package com.osrs.goals.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.osrs.goals.data.PlayerService;
import com.osrs.goals.model.Player;
import com.osrs.goals.modules.PlayerModule;

import lombok.extern.log4j.Log4j2;

/**
 * Lambda handler for setting player data.
 */
@Log4j2
public class SetPlayerHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final PlayerService playerService;

    /**
     * Default constructor used by AWS Lambda.
     */
    public SetPlayerHandler() {
        Injector injector = Guice.createInjector(new PlayerModule());
        this.playerService = injector.getInstance(PlayerService.class);
    }

    /**
     * Constructor for testing.
     *
     * @param playerService The player service to use
     */
    SetPlayerHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            Map<String, String> pathParams = input.getPathParameters();
            if (pathParams == null || !pathParams.containsKey("rsn")) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(HttpStatus.BAD_REQUEST)
                        .withBody("RSN is required in the path");
            }

            String rsn = pathParams.get("rsn");
            if (rsn.trim().isEmpty()) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(HttpStatus.BAD_REQUEST)
                        .withBody("RSN cannot be empty");
            }

            Player player = Player.builder()
                    .rsn(rsn)
                    .build();

            playerService.savePlayer(player);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.OK)
                    .withBody("Player saved successfully");

        } catch (Exception e) {
            log.error("Error processing request", e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.BAD_GATEWAY)
                    .withBody(e.getMessage());
        }
    }
}
