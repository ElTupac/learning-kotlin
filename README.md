# Kotlin Spring template

---

This is the application template for serverfull applications.

## Table of Contents

* [Requirements](#requirements)
* [Quick Start](#quick-start)
* [Java Version](#java-version)
* [Docker](#docker)
* [Localstack](#localstack)
* [Tests](#tests)
* [Migrations with Flyway](#migrations-with-flyway)
* [Swagger](#swagger)

## Requirements

* Java 17
* Docker
* Docker Compose

## Quick start

To run the application use the following steps:

1. Build the application
    ```bash
    ./gradlew clean build
    ```
2. Start external dependencies
    ```bash
   docker-compose up -d
    ```
3. Run database migrations
    ```bash
   ./gradlew migrateLocal
    ```
4. Start the application
    ```bash
   ./gradlew bootRunDev
    ```

If the linter breaks you can run `./gradlew ktlintFormat` to auto-fix the problems.
   
## Java version

In order to facilitate the Java version management we recommend using [SDKMAN](https://sdkman.io/) as the JDK version
management tool.

After installing it, run:

```bash
sdk install java 17.0.3-ms # as of time of writing this version is working, but fell free to try different distributions
sdk use java 17.0.3-ms # if is not already set as default
```

And everything should work as expected.

## Docker

Since the end of 2021 the most popular way of running Docker containers on Mac, [Docker Desktop](https://www.docker.com/products/docker-desktop/), became a paid service
for companies of medium and large size. Given that, as an alternative way of running `docker-compose`, we recommend using
[colima](https://github.com/abiosoft/colima) for a minimal setup of the docker daemon.

### 1. Installing colima

First we need to install colima to run the docker daemon. In order to do that [Homebrew](https://brew.sh/index_pt-br) is a pre-requisite.

After installing Homebrew just run:

```bash
brew install colima
```

### 2. Installing docker CLI

After that we need to install the docker CLI, so we can interact with the daemon:

```bash
brew install docker
brew install docker-compose
```

### 3. Run

Lastly we just need to start the daemon and use docker as we would normally

```bash
colima start
docker-compose up -d # should work normally
```

## Localstack

[Localstack](https://localstack.cloud/) is used to mock AWS resources. After running the `docker-compose up` you can interact with the service
in the following ways:

### Creating resources

You can create resources after the Localstack container is up by adding shell-scripts to the `shell-scripts/localstack`
folder.

These scripts can use the [aws local CLI](https://github.com/localstack/awscli-local) to create resources
(packaged with the Localstack image) and should follow the naming convention `XX_script_description.sh`, like the examples
`01_create_queues.sh` and `02_create_topics.sh`. They will be executed in ascending order.

### Interacting via CLI

After running the Localstack container you can directly interact with it via the [aws CLI](https://aws.amazon.com/pt/cli/)
(or the aforementioned awslocal).

First you need to download and configure the CLI:

```bash
brew install awscli
aws configure
```

When asked for secret key and access key, you can just write any string (localstack won't check).

#### Sending a SQS message

You can use the following command to send a SQS message.

Modify the `--messsage-body` to the payload you are testing.

Modify the `--queue-url` to the name of the queue you are testing.
```bash
aws sqs send-message \
  --queue-url http://localstack:4566/queue/user-queue-event-dev \
  --endpoint-url http://localhost:4566 \
  --message-body '{
    "event": "CREATED",
    "uuid": "0ee8a656-ed76-11ec-8ea0-0242ac120002"
  }' \
  --message-attributes '{ "contentType":{ "DataType":"String","StringValue":"application/json" } }'
```

#### Receiving a SQS message

If you are running the application, it will already start consuming the message you just sent. You can also check the message you just sent with the command:
```bash
aws sqs receive-message \
  --queue-url http://localstack:4566/queue/user-queue-event-dev \
  --endpoint-url http://localhost:4566
```

Modifying the `--queue-url` parameter with the queue name you are testing.

## Tests

### Run

To run tests with coverage use

```bash
./gradlew jacocoTestReport
```

### Repository integration tests

For the repository ITs we are using [H2 Database](http://www.h2database.com/html/main.html) for simplicity (since Hibernate abstracts this to us no change in the code
needs to be made to run the tests).

As a good practice these kinds of tests should always have the `@Rollback` annotation to correctly
reset the DB after each run.

Beyond that you can add your own scripts to populate the database with the `@Sql` annotation on the `src/test/resources/sql`
folder.

## Migrations with Flyway

Migration files should be placed at `src/main/resources/db/migration`.

To run the migrations use `./gradlew migrateLocal`.

## Swagger

We use Swagger v3, an OpenAPI implementation, to document our endpoints.
To access the documentation just run the project and go to http://localhost:8080/swagger-ui.html

## Built With

* [Kotlin](https://kotlinlang.org/) - Programming language
* [Gradle](https://gradle.org/) - Build automation tool
* [Spring](https://spring.io/) - Comprehensive programming and configuration model for modern JVM-based applications
* [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications
* [Ktlint](https://ktlint.github.io/) - Linter for Kotlin
* [Detekt](https://detekt.dev/) - Static code analyzer for Kotlin
* [JaCoCo](https://github.com/jacoco/jacoco) - Code coverage
* [MockK](https://mockk.io/) - Mocking library for Kotlin
* [Hibernate ORM](https://hibernate.org/orm/) - Database ORM and auditing features
* [AWS Spring](https://awspring.io/) - AWS SDK and Spring Boot integration
* [Swagger](https://swagger.io/) - REST API documentation
* [Flyway](https://flywaydb.org/) - Database migration tool
