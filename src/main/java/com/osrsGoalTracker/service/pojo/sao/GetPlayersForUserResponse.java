package com.osrsGoalTracker.service.pojo.sao;

import java.util.List;

import lombok.Builder;
import lombok.Value;

/**
 * Response object for the getPlayersForUser Lambda function.
 * Contains information about all players associated with a user.
 */
@Value
@Builder
public class GetPlayersForUserResponse {
    private final String userId;
    private final List<String> playerNames;
}