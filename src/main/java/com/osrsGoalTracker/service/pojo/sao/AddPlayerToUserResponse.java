package com.osrsGoalTracker.service.pojo.sao;

import lombok.Builder;
import lombok.Value;

/**
 * Response object for the addPlayerToUser Lambda function.
 * Contains information about the added player.
 */
@Value
@Builder
public class AddPlayerToUserResponse {
    private final String userId;
    private final String playerName;
}
