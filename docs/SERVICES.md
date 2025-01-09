# Service Interfaces

## Overview

The service layer contains the core business logic of the application. Each service interface defines a set of operations that can be performed on a specific domain entity or concept.

## User Service

Interface for managing user accounts and metadata.

```java
public interface UserService {
    /**
     * Creates a new user account.
     *
     * @param email The user's email address
     * @return The created user
     * @throws IllegalArgumentException if email is invalid
     * @throws ConflictException if email already exists
     */
    User createUser(String email);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The user's unique identifier
     * @return The user
     * @throws ResourceNotFoundException if user doesn't exist
     */
    User getUser(String userId);
}
```

### Implementation Details

The `UserServiceImpl` class:
- Validates email format
- Generates unique user IDs
- Manages user timestamps
- Delegates persistence to `UserRepository`

## Character Service

Interface for managing RuneScape characters associated with users.

```java
public interface CharacterService {
    /**
     * Associates a RuneScape character with a user.
     *
     * @param userId The user's unique identifier
     * @param characterName The RuneScape character name
     * @return The association details
     * @throws ResourceNotFoundException if user doesn't exist
     * @throws ConflictException if character already associated
     */
    CharacterAssociation addCharacterToUser(String userId, String characterName);

    /**
     * Retrieves all characters associated with a user.
     *
     * @param userId The user's unique identifier
     * @return List of character associations
     * @throws ResourceNotFoundException if user doesn't exist
     */
    List<CharacterAssociation> getCharactersForUser(String userId);
}
```

### Implementation Details

The `CharacterServiceImpl` class:
- Validates character names
- Manages character-user associations
- Delegates persistence to `CharacterRepository`

## Hiscores Service

Interface for retrieving OSRS player statistics.

```java
public interface HiscoresService {
    /**
     * Retrieves current hiscores for a player.
     *
     * @param playerName The RuneScape player name
     * @return The player's current stats
     * @throws ResourceNotFoundException if player not found
     * @throws ServiceException if OSRS API is unavailable
     */
    CharacterHiscores getPlayerStats(String playerName);

    /**
     * Retrieves historical hiscores for a player.
     *
     * @param playerName The RuneScape player name
     * @param timestamp The point in time to retrieve stats for
     * @return The player's historical stats
     * @throws ResourceNotFoundException if player or historical data not found
     */
    CharacterHiscores getPlayerStatsAtTime(String playerName, LocalDateTime timestamp);
}
```

### Implementation Details

The `HiscoresServiceImpl` class:
- Interacts with OSRS Hiscores API
- Caches responses for performance
- Manages historical data storage
- Delegates to `HiscoresClient` for API calls

## Goals Service

Interface for managing player skill and achievement goals.

```java
public interface GoalsService {
    /**
     * Creates a new skill goal for a player.
     *
     * @param userId The user's unique identifier
     * @param playerName The RuneScape player name
     * @param goal The goal details
     * @return The created goal
     * @throws ResourceNotFoundException if user or player not found
     */
    Goal createGoal(String userId, String playerName, GoalRequest goal);

    /**
     * Retrieves all goals for a player.
     *
     * @param userId The user's unique identifier
     * @param playerName The RuneScape player name
     * @return List of goals
     * @throws ResourceNotFoundException if user or player not found
     */
    List<Goal> getGoalsForPlayer(String userId, String playerName);

    /**
     * Updates goal progress.
     *
     * @param goalId The goal's unique identifier
     * @param progress The current progress
     * @return The updated goal
     * @throws ResourceNotFoundException if goal not found
     */
    Goal updateGoalProgress(String goalId, GoalProgress progress);
}
```

### Implementation Details

The `GoalsServiceImpl` class:
- Validates goal parameters
- Tracks goal progress
- Manages goal completion status
- Delegates persistence to `GoalsRepository`

## Common Patterns

### Error Handling

Services use custom exceptions to indicate different error conditions:

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}

public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Validation

Services perform thorough input validation:

```java
private void validateEmail(String email) {
    if (StringUtils.isBlank(email)) {
        throw new IllegalArgumentException("Email cannot be blank");
    }
    if (!EmailValidator.getInstance().isValid(email)) {
        throw new IllegalArgumentException("Invalid email format");
    }
}
```

### Dependency Injection

Services use constructor injection for dependencies:

```java
@Singleton
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Inject
    public UserServiceImpl(
            UserRepository userRepository,
            ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }
}
```

### Transactions

Services manage transactional boundaries:

```java
@Transactional
public Goal updateGoalProgress(String goalId, GoalProgress progress) {
    Goal goal = goalsRepository.findById(goalId)
        .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        
    goal.setProgress(progress);
    goal.setUpdatedAt(LocalDateTime.now());
    
    if (progress.getCurrentValue() >= goal.getTargetValue()) {
        goal.setStatus(GoalStatus.COMPLETED);
    }
    
    return goalsRepository.save(goal);
}
``` 