package com.osrs.goals.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.osrs.goals.domainlogic.GoalService;
import com.osrs.goals.modules.GoalModule;
import com.osrs.goals.service.pojo.sao.GoalCreatorResponse;

import lombok.extern.log4j.Log4j2;

/**
 * Lambda handler for creating new goals.
 */
@Log4j2
public class GoalCreatorHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;

    private final GoalService goalService;

    /**
     * Default constructor that uses Guice for dependency injection.
     */
    public GoalCreatorHandler() {
        this(Guice.createInjector(new GoalModule()).getInstance(GoalService.class));
    }

    /**
     * Constructor for testing purposes.
     *
     * @param goalService The goal service to use
     */
    @Inject
    GoalCreatorHandler(GoalService goalService) {
        this.goalService = goalService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Received request to create goal");

        GoalCreatorResponse response = goalService.createNewGoal();

        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
        try {
            apiResponse.setBody(OBJECT_MAPPER.writeValueAsString(response));
            apiResponse.setStatusCode(HTTP_OK);
        } catch (Exception e) {
            log.error("Error creating goal", e);
            throw new RuntimeException(e);
        }

        return apiResponse;
    }
}
