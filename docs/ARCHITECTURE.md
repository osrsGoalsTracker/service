# Project Architecture

## Layered Service Architecture (LSA)

The OSRS Goals Service follows a Layered Service Architecture (LSA) pattern, which is specifically designed for service-oriented applications. This architecture provides clear separation of concerns and promotes maintainability and testability.

### Layer Overview

1. **Service Abstraction Layer (SAL)**
   - AWS Lambda handlers (`*Handler.java`)
   - Request/response models
   - Input validation
   - Error handling and response formatting

2. **Domain Logic Layer (DLL)**
   - Service interfaces and implementations (`*Service.java`)
   - Business logic and rules
   - Domain model transformations
   - Core functionality implementation

3. **Domain Data Layer (DDL)**
   - Data aggregation and transformation
   - Caching strategies
   - Cross-cutting data operations
   - External service data mapping

4. **Persistence Abstraction Layer (PAL)**
   - Repository interfaces and implementations (`*Repository.java`)
   - DynamoDB operations
   - Data access patterns
   - Transaction management

5. **Dependency Abstraction Layer (DAL)**
   - External service clients (`*Client.java`)
   - Third-party API integrations
   - External system communication

### Layer Interaction Rules

1. Layers can only interact with adjacent layers
2. Communication flows downward, initiated by the upper layer
3. Each layer operates independently through well-defined interfaces
4. Dependencies are injected using Google Guice

## Package Structure

```
com.osrsGoalTracker/
├── handler/           # SAL - Lambda handlers
├── service/          # DLL - Business services
│   └── impl/        
├── data/             # DDL - Data operations
├── repository/       # PAL - Data persistence
│   └── impl/
├── external/         # DAL - External services
│   └── impl/
├── model/            # Domain models
├── exception/        # Custom exceptions
└── util/             # Utility classes
```

## Development Guidelines

### 1. Handler Implementation

- Keep handlers thin, focusing on:
  - Request parsing
  - Input validation
  - Service delegation
  - Response formatting

Example:
```java
public class GetUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Inject
    private UserService userService;
    
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        // 1. Parse input
        String userId = input.getPathParameters().get("userId");
        
        // 2. Validate input
        if (StringUtils.isBlank(userId)) {
            throw new BadRequestException("userId is required");
        }
        
        // 3. Delegate to service
        User user = userService.getUser(userId);
        
        // 4. Format response
        return ApiGatewayResponse.builder()
            .setStatusCode(200)
            .setObjectBody(user)
            .build();
    }
}
```

### 2. Service Implementation

- Focus on business logic and domain rules
- Use interfaces for abstraction
- Keep methods focused and cohesive

Example:
```java
@Singleton
public class UserServiceImpl implements UserService {
    @Inject
    private UserRepository userRepository;
    
    @Override
    public User createUser(String email) {
        // Business logic and validation
        validateEmail(email);
        
        // Create user
        User user = User.builder()
            .email(email)
            .build();
            
        return userRepository.save(user);
    }
}
```

### 3. Repository Implementation

- Focus on data persistence operations
- Handle database-specific logic
- Implement efficient data access patterns

Example:
```java
@Singleton
public class UserRepositoryImpl implements UserRepository {
    @Inject
    private DynamoDbClient dynamoDb;
    
    @Override
    public User save(User user) {
        // Convert to DynamoDB item
        Map<String, AttributeValue> item = convertToItem(user);
        
        // Save to DynamoDB
        dynamoDb.putItem(PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(item)
            .build());
            
        return user;
    }
}
```

### 4. Testing Strategy

Each layer should have comprehensive unit tests:

1. **Handler Tests**
   - Test input validation
   - Test response formatting
   - Mock service layer

2. **Service Tests**
   - Test business logic
   - Test error conditions
   - Mock repositories

3. **Repository Tests**
   - Test data operations
   - Test error handling
   - Use DynamoDB Local for integration tests

Example:
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void createUser_ValidEmail_Success() {
        // Given
        String email = "test@example.com";
        User expectedUser = User.builder().email(email).build();
        when(userRepository.save(any())).thenReturn(expectedUser);
        
        // When
        User result = userService.createUser(email);
        
        // Then
        assertThat(result).isEqualTo(expectedUser);
        verify(userRepository).save(any());
    }
}
```

## Error Handling

1. **Custom Exceptions**
   - `ResourceNotFoundException`
   - `BadRequestException`
   - `ServiceException`

2. **Error Response Format**
```json
{
    "error": {
        "code": "NOT_FOUND",
        "message": "User not found",
        "details": {
            "userId": "123"
        }
    }
}
```

## Dependency Injection

The project uses Google Guice for dependency injection:

1. **Module Configuration**
```java
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
    }
}
```

2. **Injection Usage**
```java
@Singleton
public class UserServiceImpl {
    private final UserRepository repository;
    
    @Inject
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
}
``` 