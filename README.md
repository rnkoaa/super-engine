# super-engine
Micronaut kafka Sample Application

### Kafka Setup

Using this Repo to set up a One node Kafka Cluster using k-raft

[bashj79/kafka-kraft-docker](https://github.com/bashj79/kafka-kraft-docker)

1. `git submodule add https://github.com/bashj79/kafka-kraft-docker`
2. `cd kafka-kraft-docker`
3. `docker build -t ${user}/kafka .`
4. `docker run -d -p 9092:9092 ${user}/kafka`

## Adding Additional properties from environment

`MICRONAUT_CONFIG_FILES=books/external/configs/application.yml,books/external/secret/secret.yml`

## Analyzing application properties

`http :8080/env | jq -r '.propertySources[] | select(.name == "application")' `

Project Setup using [Micronaut Kafka tutorial](https://guides.micronaut.io/latest/micronaut-kafka-gradle-kotlin.html)

## Micronaut 3.3.1 Documentation

- [User Guide](https://docs.micronaut.io/3.3.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.3.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.3.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

## Feature assertj documentation

- [https://assertj.github.io/doc/](https://assertj.github.io/doc/)

## Feature kafka documentation

- [Micronaut Kafka Messaging documentation](https://micronaut-projects.github.io/micronaut-kafka/latest/guide/index.html)

