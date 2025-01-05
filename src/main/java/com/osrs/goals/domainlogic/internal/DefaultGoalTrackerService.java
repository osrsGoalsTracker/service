package com.osrs.goals.domainlogic.internal;

import com.google.inject.Inject;
import com.osrs.goals.data.GoalTrackerDataService;
import com.osrs.goals.domainlogic.GoalTrackerService;

/**
 * Default implementation of the GoalTrackerService interface.
 * This service provides methods to interact with goal tracker data in the
 * domain layer.
 */
public class DefaultGoalTrackerService implements GoalTrackerService {
    private final GoalTrackerDataService goalTrackerDataService;

    /**
     * Constructs a new DefaultGoalTrackerService.
     *
     * @param goalTrackerDataService The GoalTrackerDataService instance to use for
     *                               data operations
     */
    @Inject
    public DefaultGoalTrackerService(GoalTrackerDataService goalTrackerDataService) {
        this.goalTrackerDataService = goalTrackerDataService;
    }
}