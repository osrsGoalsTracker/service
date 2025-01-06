package com.osrsGoalTracker.service;

import java.time.format.DateTimeFormatter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.osrsGoalTracker.data.pojo.domain.User;
import com.osrsGoalTracker.domainlogic.UserService;
import com.osrsGoalTracker.modules.UserModule;
import com.osrsGoalTracker.service.pojo.sao.CreateUserRequest;
import com.osrsGoalTracker.service.pojo.sao.GetUserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Lambda handler for creating a new user.
 * This handler processes CreateUserRequest events and returns GetUserResponse
 * objects.
 */
@Slf4j
public class CreateUserHandler implements RequestHandler<CreateUserRequest, GetUserResponse> {
    private static final Injector INJECTOR = Guice.createInjector(new UserModule());
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final UserService userService;

    /**
     * Default constructor for AWS Lambda.
     * This constructor is required by AWS Lambda to instantiate the handler.
     */
    public CreateUserHandler() {
        this(INJECTOR.getInstance(UserService.class));
    }

    /**
     * Constructs a new CreateUserHandler.
     *
     * @param userService The UserService instance to use for user operations
     */
    @Inject
    public CreateUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public GetUserResponse handleRequest(CreateUserRequest request, Context context) {
        log.info("Creating user with email: {}", request.getEmail());
        User user = userService.createUser(request.getEmail());
        return convertToResponse(user);
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
                .createdAt(user.getCreatedAt().format(DATE_TIME_FORMATTER))
                .updatedAt(user.getUpdatedAt().format(DATE_TIME_FORMATTER))
                .build();
    }
}