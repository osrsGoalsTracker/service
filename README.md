# OSRS Goals Service

A Java service for tracking Old School RuneScape player goals and progress.

## Features

- User management
- Player statistics tracking
- Goal tracking and progress monitoring

## Requirements

- JDK 21
- Gradle 8.x
- AWS CLI configured with appropriate credentials

## Installation

1. Install dependencies and build all Lambda handlers:
```bash
./gradlew build
```

2. Run tests:
```bash
./gradlew test
```

## Lambda Handlers

The service is composed of multiple Lambda functions, each packaged as a separate JAR:

- `getPlayerStats-lambda.jar` - Retrieves player statistics from OSRS Hiscores
- `getUser-lambda.jar` - Retrieves user metadata
- `createUser-lambda.jar` - Creates a new user account

### Building Individual Handlers

Build specific Lambda handlers:
```bash
# Build GetPlayerStats handler
./gradlew buildGetPlayerStatsHandler

# Build GetUser handler
./gradlew buildGetUserHandler

# Build CreateUser handler
./gradlew buildCreateUserHandler
```

### Building All Handlers

Build all Lambda handlers at once:
```bash
# Using the build task (includes all handlers)
./gradlew build

# Or explicitly build all handlers
./gradlew buildAllHandlers
```

The built JAR files will be located in `build/libs/`:
- `getPlayerStats-lambda.jar`
- `getUser-lambda.jar`
- `createUser-lambda.jar`

## API Endpoints

The service exposes the following endpoints through AWS API Gateway:

### POST /users

Creates a new user account.

**Request Body:**
```json
{
    "email": "string"
}
```

**Response:**
```json
{
    "userId": "string",
    "email": "string",
    "createdAt": "string",
    "updatedAt": "string"
}
```

### GET /users/{userId}

Retrieves user metadata.

**Parameters:**
- `userId` (path parameter) - The user's unique identifier

**Response:**
```json
{
    "userId": "string",
    "email": "string",
    "createdAt": "string",
    "updatedAt": "string"
}
```

### GET /users/{userId}/players/{rsn}/stats

Retrieves player's OSRS stats for a specific user's registered player.

**Parameters:**
- `userId` (path parameter) - The user's unique identifier
- `rsn` (path parameter) - RuneScape username

**Response:**
```json
{
    "rsn": "string",
    "stats": {
        "overall": {
            "rank": number,
            "level": number,
            "xp": number
        }
    }
}
```

## Architecture

The service follows a layered service architecture (LSA) with the following layers:

1. **Service Abstraction Layer (SAL)**
   - Lambda handlers for API endpoints
   - Request/response models
   - Error handling

2. **Domain Logic Layer (DLL)**
   - Business logic services
   - Domain models
   - Validation rules

3. **Domain Data Layer (DDL)**
   - Data access services
   - Data transformation
   - Caching (if needed)

4. **Persistence Abstraction Layer (PAL)**
   - Repository interfaces
   - Data access objects (DAOs)
   - Database interactions

5. **Dependency Abstraction Layer (DAL)**
   - External service clients
   - Third-party integrations

## Dependencies

- AWS Lambda Core - Lambda function support
- AWS Lambda Events - Event handling
- AWS DynamoDB - Database operations
- Google Guice - Dependency injection
- Jackson - JSON serialization
- Log4j2 - Logging
- Lombok - Boilerplate reduction
- JUnit 5 - Testing
- Mockito - Mocking for tests

## Infrastructure

The service is deployed using AWS CDK with the following components:

- API Gateway for REST endpoints
- Lambda functions for business logic
- DynamoDB tables for data storage
- IAM roles and permissions

For infrastructure deployment and management, see the [CDK README](../cdk/README.md).

## Development

### Building
```bash
./gradlew build
```

### Testing
```bash
./gradlew test
```

### Code Style
The project uses checkstyle for code style enforcement. Configuration is pulled from:
```
https://raw.githubusercontent.com/osrsGoalsTracker/java-build-config/refs/heads/main/checkstyle/checkstyle.xml
```

### Git Hooks
The project includes pre-push hooks for code quality checks. These are installed automatically during build.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 
