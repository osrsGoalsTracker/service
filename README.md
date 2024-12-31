# OSRS Goals Service

A Java-based AWS Lambda service for tracking Old School RuneScape player goals and stats.

## Overview

This service provides a REST API for managing OSRS player data and goals. It follows a Layered Service Architecture (LSA) pattern with dependency injection for clean separation of concerns. The service integrates with the OSRS Hiscores to fetch player statistics.

## Architecture

The service follows LSA with the following layers:

1. **Service Abstraction Layer (SAL)**
   - Lambda handlers for API endpoints
   - Service Abstraction Objects (SAOs) for API responses

2. **Domain Logic Layer (DLL)**
   - Core business logic services
   - Domain logic implementation

3. **Data Layer (DDL)**
   - Coordinates between dependencies and persistence
   - Domain objects and data coordination
   - Child layers:
     - **Dependency Layer**: External service integrations (OSRS Hiscores)
     - **Persistence Layer**: Data storage operations (future implementation)

## Lambda Handlers

### Get Player Stats
- **Handler**: `GetPlayerStatsHandler`
- **JAR**: `getPlayerStats-lambda.jar`
- **Endpoint**: `GET /players/{rsn}/stats`
- **Description**: Retrieves a player's current stats from the OSRS Hiscores

## Prerequisites

- Java 21
- Gradle 8.5+ (or use the Gradle wrapper included in the project)
- AWS Account (for Lambda deployment)

## Building

```bash
# Build all Lambda handlers
./gradlew build

# Build specific handlers
./gradlew buildGetPlayerStatsHandler  # Builds getPlayerStats-lambda.jar

# Build all handlers explicitly
./gradlew buildAllHandlers
```

The build process creates separate JAR files for each Lambda handler in `build/libs/`:
- `getPlayerStats-lambda.jar` - Complete deployment package for the GetPlayerStats handler
- Additional handlers will be added as they are implemented

## Testing

```bash
# Run all tests
./gradlew test
```

## API Endpoints

### Get Player OSRS Stats

Retrieves the current OSRS stats for a player from the official OSRS Hiscores.

```
GET /players/{rsn}/stats
```

#### Parameters
- `rsn` (path) - The RuneScape name of the player

#### Response Format
```json
{
    "username": "string",
    "skills": {
        "skillName": {
            "name": "string",
            "rank": "integer",
            "level": "integer",
            "experience": "integer"
        }
        // ... other skills
    },
    "activities": {
        "activityName": {
            "name": "string",
            "rank": "integer",
            "score": "integer"
        }
        // ... other activities
    }
}
```

#### Example Response
```json
{
    "username": "Zezima",
    "skills": {
        "Attack": {
            "name": "Attack",
            "rank": 1337,
            "level": 99,
            "experience": 13034431
        }
        // ... other skills
    },
    "activities": {
        "Clue Scrolls (all)": {
            "name": "Clue Scrolls (all)",
            "rank": 42,
            "score": 500
        }
        // ... other activities
    }
}
```

#### Error Responses
- `400 Bad Request` - If the RSN is null, empty, or contains only whitespace
- `500 Internal Server Error` - If there's an error fetching the stats from OSRS Hiscores

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── osrs/
│               └── goals/
│                   ├── service/              # Service Abstraction Layer
│                   │   ├── GetPlayerStatsHandler.java
│                   │   └── pojo/
│                   │       └── sao/
│                   │           └── PlayerStatsResponse.java
│                   ├── domainlogic/          # Domain Logic Layer
│                   │   ├── StatsService.java
│                   │   └── internal/
│                   │       └── DefaultStatsService.java
│                   ├── data/                 # Data Layer (Parent)
│                   │   ├── StatsDataService.java
│                   │   ├── internal/
│                   │   │   └── DefaultStatsDataService.java
│                   │   └── pojo/
│                   │       └── domain/
│                   │           └── PlayerStats.java
│                   ├── dependency/           # Dependency Layer (Child of Data)
│                   │   ├── HiscoresClient.java
│                   │   └── internal/
│                   │       └── OsrsHiscoresClient.java
│                   └── modules/              # Dependency Injection
│                       └── StatsModule.java
└── test/
    └── java/
        └── com/
            └── osrs/
                └── goals/
                    ├── service/
                    │   └── GetPlayerStatsHandlerTest.java
                    ├── domainlogic/
                    │   └── internal/
                    │       └── DefaultStatsServiceTest.java
                    ├── data/
                    │   └── internal/
                    │       └── DefaultStatsDataServiceTest.java
                    └── dependency/
                        └── internal/
                            └── OsrsHiscoresClientTest.java
```

## Development

The service uses:
- Gradle for build management and multi-handler deployment
- JUnit 5 for testing
- Mockito for mocking in tests
- Lombok for reducing boilerplate
- Google Guice for dependency injection
- AWS Lambda for serverless execution
- Log4j2 for logging
- OSRS Hiscores library for fetching player stats

### Lambda Optimization
- Uses static Guice injector to maintain singleton instances across invocations
- Each handler is packaged into its own deployment JAR
- All dependencies are thread-safe for concurrent execution

## Contributing

1. Create a feature branch
2. Make your changes
3. Write tests
4. Create a pull request

## License

MIT 
