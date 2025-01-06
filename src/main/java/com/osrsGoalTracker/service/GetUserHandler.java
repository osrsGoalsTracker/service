package com.osrsGoalTracker.service;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.osrsGoalTracker.data.pojo.domain.User;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.modules.UserModule;
import com.osrsGoalTracker.service.pojo.sao.GetUserResponse;

import lombok.extern.log4j.Log4j2;

/**
 * Lambda handler for retrieving user metadata.
 * This handler processes API Gateway events to retrieve user information.
 */
@Log4j2
public class GetUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_SERVER_ERROR = 500;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final UserService userService;

    /**
     * Default constructor for AWS Lambda.
     * This constructor is required by AWS Lambda to instantiate the handler.
     */
    public GetUserHandler() {
        Injector injector = Guice.createInjector(new UserModule());
        this.userService = injector.getInstance(UserService.class);
    }

    /**
     * Constructor for testing purposes.
     * Allows injection of mock services in tests.
     *
     * @param userService The UserService instance to use for retrieving user data
     */
    @Inject
    GetUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            if (input == null) {
                return createErrorResponse(HTTP_BAD_REQUEST, "Request cannot be null");
            }

            Map<String, String> pathParameters = input.getPathParameters();
            if (pathParameters == null) {
                return createErrorResponse(HTTP_BAD_REQUEST, "Path parameters cannot be null");
            }

            String userId = pathParameters.get("userId");
            if (userId == null || userId.trim().isEmpty()) {
                return createErrorResponse(HTTP_BAD_REQUEST, "User ID cannot be null or empty");
            }

            userId = userId.trim();
            log.info("Received request to get user with ID: {}", userId);
            User user = userService.getUser(userId);
            GetUserResponse response = convertToResponse(user);

            APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
            apiResponse.setStatusCode(HTTP_OK);
            apiResponse.setBody(OBJECT_MAPPER.writeValueAsString(response));
            return apiResponse;
        } catch (Exception e) {
            log.error("Error processing request", e);
            return createErrorResponse(HTTP_SERVER_ERROR, "Error processing request: " + e.getMessage());
        }
    }

    /**
     * Converts a User domain object to a GetUserResponse.
     *
     * @param user The User object to convert
     * @return The converted GetUserResponse
     */
    private GetUserResponse convertToResponse(User user) {
        return GetUserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .createdAt(DATE_TIME_FORMATTER.format(user.getCreatedAt()))
                .updatedAt(DATE_TIME_FORMATTER.format(user.getUpdatedAt()))
                .build();
    }

    private APIGatewayProxyResponseEvent createErrorResponse(int statusCode, String message) {
        log.error(message);
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(statusCode);
        response.setBody(String.format("{\"message\":\"%s\"}", message));
        return response;
    }
}
