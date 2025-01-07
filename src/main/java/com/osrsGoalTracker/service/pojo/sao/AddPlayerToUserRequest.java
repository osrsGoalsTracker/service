package com.osrsGoalTracker.service.pojo.sao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for adding a RuneScape player to a user's account.
 */
@Data
@NoArgsConstructor
public class AddPlayerToUserRequest {
    private String playerName;
}