package com.osrsGoalTracker.goal.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Value;

/**
 * Domain model representing a goal.
 */
@Value
@Builder
public class Goal {
    /**
     * The ID of the user who owns this goal.
     */
    private final String userId;

    /**
     * The ID of the goal.
     */
    private final String goalId;

    /**
     * The name of the character this goal is for.
     */
    private final String characterName;

    /**
     * The skill or activity being tracked (e.g., "WOODCUTTING", "BOUNTY_HUNTER").
     */
    private final String targetAttribute;

    /**
     * The type of target (e.g., "xp", "level", etc.).
     */
    private final String targetType;

    /**
     * The target value to achieve.
     */
    private final long targetValue;

    /**
     * The date by which to achieve the goal.
     */
    private final Instant targetDate;

    /**
     * The type of notification channel to use.
     */
    private final String notificationChannelType;

    /**
     * How often to check/notify about progress.
     */
    private final String frequency;
}