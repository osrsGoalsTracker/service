package com.osrsGoalTracker.handlers.pojo.sao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for adding a RuneScape character to a user's account.
 */
@Data
@NoArgsConstructor
public class AddCharacterToUserRequest {
    private String characterName;
}
