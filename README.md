# OSRS Goals Service

A Java-based AWS Lambda service for tracking Old School RuneScape player goals.

## Project Structure

The project consists of two main components:

1. `src/` - Java service with Lambda handlers and business logic

## Prerequisites

- Java 21
- Gradle 8.5+ (or use the Gradle wrapper included in the project)

## Building the Service

### Build the Java Service

```bash
# Build the project
./gradlew build

# Create a fat JAR with all dependencies (for AWS Lambda deployment)
./gradlew buildFatJar
```

## Testing

### Run Java Tests

```bash
./gradlew test
```

## API Endpoints

### Get Player OSRS Stats

Retrieves the current OSRS stats for a player.

```
GET /players/{rsn}/stats
```

#### Parameters

- `rsn` (path) - The RuneScape name of the player

#### Responses

- `200 OK` - Returns the player's stats
- `400 Bad Request` - RSN is missing or empty
- `404 Not Found` - Player stats not found
- `502 Bad Gateway` - Error fetching stats from OSRS Hiscores

#### Example Response (200 OK)

```json
{
  "rsn": "player123",
  "skills": [
    {
      "name": "Attack",
      "level": 99,
      "experience": 13034431
    }
  ],
  "activities": [
    {
      "name": "Clue Scrolls (all)",
      "rank": 12345,
      "score": 100
    }
  ]
}
```

### Set Player

Creates or updates a player in the system.

```
PUT /players/{rsn}
```

#### Parameters

- `rsn` (path) - The RuneScape name of the player

#### Responses

- `200 OK` - Player successfully created/updated
- `400 Bad Request` - RSN is missing or empty

## Development

The service follows a layered architecture:

1. Lambda Layer (`lambda/`) - Handles AWS Lambda requests
2. Data Layer (`data/`) - Business logic and data coordination
3. Persistence Layer (`persistence/`) - Data storage operations
4. External Layer (`external/`) - External service interactions

## Contributing

1. Create a feature branch
2. Make your changes
3. Write tests
4. Create a pull request

## License

MIT 
