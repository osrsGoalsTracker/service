package com.osrsGoalTracker.handlers.pojo.sao;

import lombok.Builder;
import lombok.Value;

/**
 * Response object for the addCharacterToUser Lambda function.
 * Contains information about the added character.
 */
@Value
@Builder
public class AddCharacterToUserResponse {
    private final String userId;
    private final String characterName;
}

