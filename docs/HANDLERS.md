# Lambda Handlers

## Overview

The service exposes its functionality through AWS Lambda handlers, each implementing specific API endpoints. All handlers follow a consistent pattern:

1. Parse and validate input
2. Delegate to appropriate service
3. Format and return response

## User Management

### CreateUserHandler

Creates a new user account.

**Endpoint**: `POST /users`

**Request**:
```json
{
    "email": "string"
}
```

**Response**:
```json
{
    "userId": "string",
    "email": "string",
    "createdAt": "string",
    "updatedAt": "string"
}
```

**Error Cases**:
- 400: Invalid email format
- 409: Email already exists

### GetUserHandler

Retrieves user information.

**Endpoint**: `GET /users/{userId}`

**Path Parameters**:
- `userId`: User's unique identifier

**Response**:
```json
{
    "userId": "string",
    "email": "string",
    "createdAt": "string",
    "updatedAt": "string"
}
```

**Error Cases**:
- 400: Invalid userId format
- 404: User not found

## Player Management

### AddPlayerToUserHandler

Associates a RuneScape player with a user account.

**Endpoint**: `POST /users/{userId}/players`

**Path Parameters**:
- `userId`: User's unique identifier

**Request**:
```json
{
    "playerName": "string"
}
```

**Response**:
```json
{
    "userId": "string",
    "playerName": "string"
}
```

**Error Cases**:
- 400: Invalid player name
- 404: User not found
- 409: Player already associated

### GetPlayersForUserHandler

Retrieves all players associated with a user.

**Endpoint**: `GET /users/{userId}/players`

**Path Parameters**:
- `userId`: User's unique identifier

**Response**:
```json
{
    "userId": "string",
    "playerNames": [
        "string"
    ]
}
```

**Error Cases**:
- 400: Invalid userId format
- 404: User not found

## Player Statistics

### GetPlayerStatsHandler

Retrieves OSRS hiscores for a player.

**Endpoint**: `GET /users/{userId}/players/{playerName}/stats`

**Path Parameters**:
- `userId`: User's unique identifier
- `playerName`: RuneScape player name

**Response**:
```json
{
    "playerName": "string",
    "stats": {
        "overall": {
            "rank": number,
            "level": number,
            "xp": number
        },
        "attack": {
            "rank": number,
            "level": number,
            "xp": number
        },
        // ... other skills
    }
}
```

**Error Cases**:
- 400: Invalid player name
- 404: User or player not found
- 502: OSRS hiscores unavailable

## Common Response Formats

### Success Response

All successful responses follow this format:
```json
{
    "statusCode": number,
    "body": object,
    "headers": {
        "Content-Type": "application/json"
    }
}
```

### Error Response

All error responses follow this format:
```json
{
    "statusCode": number,
    "body": {
        "error": {
            "code": "string",
            "message": "string",
            "details": object
        }
    },
    "headers": {
        "Content-Type": "application/json"
    }
}
```

## Handler Implementation Pattern

All handlers follow this implementation pattern:

```java
public class SomeHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Inject
    private SomeService service;
    
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            // 1. Parse input
            SomeRequest request = parseInput(input);
            
            // 2. Validate
            validateRequest(request);
            
            // 3. Execute
            SomeResponse response = service.doSomething(request);
            
            // 4. Return
            return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(response)
                .build();
                
        } catch (Exception e) {
            return handleError(e);
        }
    }
}
```

## Error Handling

Handlers use a common error handling approach:

```java
private APIGatewayProxyResponseEvent handleError(Exception e) {
    if (e instanceof ResourceNotFoundException) {
        return ApiGatewayResponse.builder()
            .setStatusCode(404)
            .setObjectBody(new ErrorResponse("NOT_FOUND", e.getMessage()))
            .build();
    }
    
    if (e instanceof BadRequestException) {
        return ApiGatewayResponse.builder()
            .setStatusCode(400)
            .setObjectBody(new ErrorResponse("BAD_REQUEST", e.getMessage()))
            .build();
    }
    
    // Default to internal server error
    return ApiGatewayResponse.builder()
        .setStatusCode(500)
        .setObjectBody(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"))
        .build();
} 