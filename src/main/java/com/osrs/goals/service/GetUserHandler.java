package com.osrs.goals.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Inject;
import com.osrs.goals.data.pojo.domain.User;
import com.osrs.goals.domainlogic.UserService;
import com.osrs.goals.service.pojo.sao.GetUserRequest;
import com.osrs.goals.service.pojo.sao.GetUserResponse;

/**
 * Lambda handler for retrieving user metadata.
 * This handler processes GetUserRequest events and returns GetUserResponse
 * objects.
 */
public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {
    private final UserService userService;

    /**
     * Constructs a new GetUserHandler.
     *
     * @param userService The UserService instance to use for retrieving user data
     */
    @Inject
    public GetUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public GetUserResponse handleRequest(GetUserRequest request, Context context) {
        User user = userService.getUser(request.getUserId());
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
                .withUserId(user.getUserId())
                .withEmail(user.getEmail())
                .withCreatedAt(user.getCreatedAt())
                .withUpdatedAt(user.getUpdatedAt())
                .build();
    }
}
