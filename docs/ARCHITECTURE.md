# Project Architecture

## Layered Architecture Rule Set

This project follows a strict layered architecture pattern designed for AWS Lambda-based microservices. Each domain (e.g., User, Character, Hiscore) is organized into its own module with clear layer separation.

## Layer Definitions

### 1. Handler Layer (`*.handler`)
- Entry points for AWS Lambda functions
- Handles API Gateway events and responses
- Follows a strict 4-step pattern:
  1. Parse input
  2. Validate input
  3. Run service function
  4. Return output
- Only depends on the service layer
- Located in `*.handler` packages

### 2. Service Layer (`*.service`)
- Contains core business logic
- Defines interfaces and implementations
- Uses repository interfaces for data access
- Manages transactions and orchestration
- Located in `*.service` packages
- Implementations in `*.service.impl`

### 3. Repository Layer (`*.repository`)
- Abstracts data persistence operations
- Provides DynamoDB implementations
- Handles data mapping and queries
- Located in `*.repository` packages
- Implementations in `*.repository.impl`

### 4. Model Layer (`*.model`)
- Domain models using Lombok
- Request/Response DTOs
- Uses `@Value` and `@Builder` for immutability
- Located in `*.model` packages

### 5. External Layer (`*.external`)
- Handles third-party integrations (e.g., OSRS Hiscores)
- Abstracts external API calls
- Located in `*.external` packages
- Implementations in `*.external.impl`

### 6. Dependency Injection Layer (`*.di`)
- Google Guice modules for each domain
- Binds interfaces to implementations
- Located in `*.di` packages

## Domain Modules

### User Domain (`com.osrsGoalTracker.user`)
- User account management
- Authentication and authorization
- Profile management

### Character Domain (`com.osrsGoalTracker.character`)
- OSRS character management
- Character-user associations
- Character metadata

### Hiscore Domain (`com.osrsGoalTracker.hiscore`)
- OSRS hiscores integration
- Skill and activity tracking
- Historical data management

## Layer Interaction Rules

1. **Handler Layer**
   - Can only call Service layer
   - Must follow 4-step pattern
   - Must handle API Gateway events

2. **Service Layer**
   - Can call Repository layer
   - Can call other Services
   - Can call External layer
   - Manages transactions

3. **Repository Layer**
   - Can only access data store
   - No service layer calls
   - No cross-repository calls

4. **External Layer**
   - Can only call external APIs
   - Must handle retries and failures
   - Must provide clean interfaces

## Package Structure Example (User Domain)

```
com.osrsGoalTracker.user/
├── di/
│   └── UserModule.java
├── handler/
│   ├── CreateUserHandler.java
│   ├── GetUserHandler.java
│   ├── request/
│   │   └── CreateUserRequest.java
│   └── response/
│       └── GetUserResponse.java
├── model/
│   └── User.java
├── repository/
│   ├── UserRepository.java
│   └── impl/
│       └── UserRepositoryImpl.java
└── service/
    ├── UserService.java
    └── impl/
        └── UserServiceImpl.java
```

## Development Guidelines

### 1. Handler Implementation
```java
public class CreateUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
public class GetUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Inject

    
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        CreateUserRequest request = parseInput(input);
        String userId = input.getPathParameters().get("userId");
        
        validateRequest(request);
        }
        // 3. Run service function
        User user = userService.createUser(request);
        User user = userService.getUser(userId);
        // 4. Return output
        // 4. Format response
        return ApiGatewayResponse.builder()
            .setStatusCode(200)
            .setObjectBody(user)
            .build();
    }
}
```
### 2. Model Implementation
```java
@Value
@Builder
public class User {
    String userId;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
```

### 3. Service Implementation
Example:
```java
@Singleton
    private final UserRepository userRepository;

public class UserServiceImpl implements UserService {
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public User createUser(CreateUserRequest request) {
        // Business logic here
        return userRepository.save(user);
    }
}
```
### 4. Repository Implementation
Example:
```java
@Singleton
    private final DynamoDbClient dynamoDb;

public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(DynamoDbClient dynamoDb) {
        this.dynamoDb = dynamoDb;
    }

    
    @Override
        // DynamoDB operations here
        return user;
    }
}
```
## Testing Strategy
Each layer should have comprehensive unit tests:

   - Test input parsing
   - Test validation
   - Mock service layer
   - Test input validation
   - Mock service layer

2. **Service Tests**
   - Test error conditions
   - Test error cases
   - Test transactions
   - Mock repositories

3. **Repository Tests**
   - Use DynamoDB Local
   - Test queries
   - Test data operations

4. **Integration Tests**
   - Test full flow
   - Use test containers
   - Test error scenarios

## Build and Deployment

Each Lambda handler is built into its own JAR file using Gradle tasks:

```bash
# Build individual handlers
./gradlew buildCreateUserHandler
./gradlew buildGetUserHandler
./gradlew buildAddCharacterToUserHandler

# Build all handlers
./gradlew buildAllHandlers
}
``` 