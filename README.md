# OSRS Goals Service

A Java-based AWS Lambda service for tracking Old School RuneScape player goals.

## Project Structure

The project consists of two main components:

1. `src/` - Java service with Lambda handlers and business logic

## Prerequisites

- Java 21
- Maven

## Building the Service

### Build the Java Service

```bash
mvn clean package
```

## Testing

### Run Java Tests

```bash
mvn test
```

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
