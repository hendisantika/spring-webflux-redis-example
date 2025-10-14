# Spring WebFlux Redis Example

A reactive REST API example using Spring Boot 3, Spring WebFlux, and Redis. This project demonstrates how to build a
non-blocking, reactive application with Redis as a data store.

## Technologies Used

- **Java 21** - Programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring WebFlux** - Reactive web framework
- **Spring Data Redis Reactive** - Reactive Redis integration
- **Redis** - In-memory data store
- **Lombok** - Reduce boilerplate code
- **Gradle 8.12** - Build tool
- **Docker Compose** - Container orchestration for local development

## Features

- Reactive REST API using Spring WebFlux
- Non-blocking Redis operations with ReactiveRedisTemplate
- Automatic test data loading on startup
- Docker Compose integration for easy local development
- JSON serialization/deserialization for Redis storage

## Prerequisites

- Java 21 or higher
- Docker (for running Redis via Docker Compose)
- Gradle 8.12 or higher (wrapper included)

## Project Structure

```
src/main/java/com/hendisantika/redisexample/
├── SpringWebfluxRedisExampleApplication.java  # Main application class
├── config/
│   └── RedisConfiguration.java                # Redis configuration
├── controller/
│   └── ProductController.java                 # REST API endpoints
├── data/
│   └── ProductLoader.java                     # Test data loader
├── model/
│   ├── Product.java                           # Product entity
│   └── ProductResponse.java                   # API response model
├── repository/
│   └── ProductRepository.java                 # Redis data access
└── service/
    └── ProductService.java                    # Business logic
```

## Setup and Installation

### 1. Clone the repository

```bash
git clone <repository-url>
cd spring-webflux-redis-example
```

### 2. Configure Java Version (if needed)

If you have multiple Java versions installed, create a `gradle.properties` file:

```properties
org.gradle.java.home=/path/to/java21
```

### 3. Build the project

```bash
./gradlew clean build
```

## Running the Application

### Option 1: Using Gradle with Docker Compose (Recommended)

This will automatically start Redis in Docker and run the application:

```bash
./gradlew bootRunLocal
```

### Option 2: Using Gradle with existing Redis

If you already have Redis running on localhost:6379:

```bash
./gradlew bootRun
```

### Option 3: Manual Docker Compose

Start Redis:

```bash
docker-compose up -d
```

Run the application:

```bash
./gradlew bootRun
```

Stop Redis:

```bash
docker-compose down
```

### Using Gradle Tasks

The project includes custom Gradle tasks:

- `./gradlew startRedis` - Start Redis container
- `./gradlew stopRedis` - Stop Redis container
- `./gradlew bootRunLocal` - Start Redis and run application
- `./gradlew bootRunLocalDebug` - Start Redis and run with debugger (port 5005)

## Application Configuration

The application runs on **port 8080** by default (configured in `application.properties`).

Redis connection:

- Host: localhost
- Port: 6379
- No password required for local development

## API Endpoints

### Get Product by ID

```bash
GET /products/{id}
```

**Example Request:**

```bash
curl http://localhost:8080/products/1
```

**Example Response (200 OK):**

```json
{
  "id": "1",
  "name": "Product1",
  "description": "Product1 Description",
  "active": true,
  "start_date": "2021-01-01T00:00Z",
  "end_date": "2021-01-31T00:00Z"
}
```

**Example Response (404 Not Found):**

```bash
# When product doesn't exist
curl http://localhost:8080/products/999
# Returns empty response with 404 status
```

## Test Data

The application automatically loads 10 sample products (IDs 1-10) into Redis on startup:

- Odd-numbered products are active (1, 3, 5, 7, 9)
- Even-numbered products are inactive (2, 4, 6, 8, 10)

## Testing

Run the tests:

```bash
./gradlew test
```

## Technical Details

### Reactive Programming

This application uses Project Reactor for reactive programming:

- `Mono<T>` - Represents 0 or 1 asynchronous result
- Non-blocking I/O operations
- Backpressure support

### Redis Operations

The application uses `ReactiveRedisTemplate` with:

- String keys
- Jackson JSON serialization for Product objects
- Reactive operations (non-blocking)

### Key Components

1. **RedisConfiguration** - Configures ReactiveRedisOperations with JSON serialization
2. **ProductLoader** - Uses `@PostConstruct` to load test data on startup
3. **ProductRepository** - Provides reactive Redis operations
4. **ProductService** - Business logic layer
5. **ProductController** - REST API endpoints with reactive responses

## Development

### Hot Reload

The application includes Spring Boot DevTools for automatic restart during development.

### Debugging

Run with debugger:

```bash
./gradlew bootRunLocalDebug
```

The debugger will be available on port 5005.

## Troubleshooting

### Port 6379 already in use

If Redis is already running:

```bash
# Check what's using the port
lsof -i :6379

# Stop existing Redis
docker stop $(docker ps -q --filter ancestor=redis)
```

### Port 8080 already in use

Change the port in `src/main/resources/application.properties`:

```properties
server.port=8081
```

### Build fails with "Unsupported class file major version"

Ensure you're using Java 21:

```bash
java -version
# Should show Java 21

# Set JAVA_HOME or create gradle.properties with correct Java path
echo "org.gradle.java.home=/path/to/java21" > gradle.properties
```

## Author

- Name: hendisantika
- Email: hendisantika@gmail.com
- Telegram: @hendisantika34

## License

This project is created for educational purposes.
