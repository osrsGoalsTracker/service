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
import com.osrs.goals.service.pojo.sao.CreateGoalResponse;

import lombok.extern.log4j.Log4j2;

/**
 * AWS Lambda handler for creating goals.
 * This class serves as the entry point for the Lambda function and coordinates
 * the request processing through the application layers.
 */
@Log4j2
public class CreateGoalHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_SERVER_ERROR = 500;

    private final GoalService goalService;

    /**
     * Default constructor that uses Guice for dependency injection.
     */
    public CreateGoalHandler() {
        this(Guice.createInjector(new GoalModule()).getInstance(GoalService.class));
    }

    /**
     * Constructor for testing purposes.
     *
     * @param goalService The service for managing goals
     */
    @Inject
    CreateGoalHandler(GoalService goalService) {
        this.goalService = goalService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            log.info("Handling create goal request");
            CreateGoalResponse response = goalService.createGoal();

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HTTP_OK)
                    .withBody(OBJECT_MAPPER.writeValueAsString(response));
        } catch (Exception e) {
            log.error("Error creating goal: {}", e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HTTP_SERVER_ERROR)
                    .withBody(String.format("{\"message\":\"%s\"}", e.getMessage()));
        }
    }
}
