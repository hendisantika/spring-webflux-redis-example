# Spring WebFlux Redis Example

[![Java CI with Gradle](https://github.com/hendisantika/spring-webflux-redis-example/actions/workflows/gradle.yml/badge.svg)](https://github.com/hendisantika/spring-webflux-redis-example/actions/workflows/gradle.yml)
![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen)
![Gradle](https://img.shields.io/badge/Gradle-9.8.2-02303A)

A reactive REST API example using Spring Boot 4, Spring WebFlux, and Redis. This project demonstrates how to build a
non-blocking, reactive application with Redis as a data store.

## Technologies Used

- **Java 25** - Programming language (via Gradle Java toolchain)
- **Spring Boot 4.1.2** - Application framework
- **Spring WebFlux** - Reactive web framework
- **Spring Data Redis Reactive** - Reactive Redis integration
- **Redis** - In-memory data store
- **Jackson 3** - JSON serialization (`JacksonJsonRedisSerializer`)
- **Lombok** - Reduce boilerplate code
- **Gradle 9.8.2** - Build tool
- **Docker Compose** - Container orchestration for local development

## Features

- Reactive REST API using Spring WebFlux
- Non-blocking Redis operations with ReactiveRedisTemplate
- Automatic test data loading on startup
- Docker Compose integration for easy local development
- JSON serialization/deserialization for Redis storage

## Prerequisites

- JDK 25
- Docker (for running Redis via Docker Compose)
- Gradle 9.8.2 (wrapper included, no local install needed)

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

The build uses a [Gradle Java toolchain](https://docs.gradle.org/current/userguide/toolchains.html) pinned to
**JDK 25**, so Gradle will pick up an installed JDK 25 automatically. If you have multiple JDKs, point `JAVA_HOME`
to JDK 25:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)   # macOS
# or with SDKMAN
sdk use java 25-tem
```

> Do **not** commit `org.gradle.java.home` to `gradle.properties` – a machine-specific path breaks the CI build.
> Put it in `~/.gradle/gradle.properties` instead if you need it.

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
- Jackson 3 JSON serialization (`JacksonJsonRedisSerializer`) for Product objects
- Reactive operations (non-blocking)

### Key Components

1. **RedisConfiguration** - Configures ReactiveRedisOperations with Jackson 3 JSON serialization
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

Ensure you're using JDK 25:

```bash
java -version
# Should show Java 25

# Point JAVA_HOME to JDK 25 (or set org.gradle.java.home in ~/.gradle/gradle.properties)
export JAVA_HOME=/path/to/jdk-25
```

## Continuous Integration

GitHub Actions workflow [`.github/workflows/gradle.yml`](.github/workflows/gradle.yml) runs on every push and pull
request to `main`:

- **build** – sets up Temurin JDK 25 and runs `./gradlew build` (compile + tests)
- **dependency-submission** – submits the Gradle dependency graph so Dependabot alerts cover all dependencies

## Dependency Updates (Dependabot)

[`.github/dependabot.yml`](.github/dependabot.yml) keeps dependencies up to date:

| Ecosystem      | Schedule                    | Grouping                                                      | Commit prefix   |
|----------------|-----------------------------|---------------------------------------------------------------|-----------------|
| Gradle         | daily 05:00 Asia/Jakarta    | `spring-boot`, `gradle-plugins`, `test-dependencies`, `lombok` | `build(deps)`   |
| GitHub Actions | daily 05:00 Asia/Jakarta    | all minor/patch updates in one PR                             | `ci(deps)`      |
| Docker Compose | weekly (Monday) 05:00       | –                                                             | `chore(deps)`   |

PRs are labeled (`dependencies` + ecosystem label) and assigned to the maintainer.

## Author

- Name: hendisantika
- Email: hendisantika@gmail.com
- Telegram: @hendisantika34

## License

This project is created for educational purposes.
