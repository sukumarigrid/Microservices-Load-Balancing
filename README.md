# Microservices Service Discovery

This project demonstrates client-side service discovery with Netflix Eureka.

## Services

- `eureka-server`: service registry UI on `http://localhost:8761`
- `dependency-service`: sample dependency registered as `DEPENDENCY-SERVICE`
- `consumer-service`: calls `http://dependency-service/api/message` through a load-balanced Eureka client

## Run

```bash
docker compose up --build
```

After the services register, open the Eureka dashboard:

```text
http://localhost:8761
```

Call the consumer several times:

```bash
curl http://localhost:8080/api/dependency-message
```

The `dependency.instanceId` or `dependency.port` value should alternate between the two registered dependency-service
instances (`8081` and `8082`), showing client-side load balancing through Eureka.

## Run A Second Instance Manually

You can also run another dependency instance outside Docker on a different port:

```bash
mvn -pl dependency-service spring-boot:run \
  -Dspring-boot.run.arguments="--server.port=8091 --eureka.client.service-url.defaultZone=http://localhost:8761/eureka/"
```

Then refresh `http://localhost:8761` and verify a third `DEPENDENCY-SERVICE` instance appears.

## Screenshot Checklist For Review

Attach these screenshots to the merge request:

- Eureka dashboard showing `CONSUMER-SERVICE` and multiple `DEPENDENCY-SERVICE` instances.
- Repeated `curl http://localhost:8080/api/dependency-message` responses showing different dependency ports or instance IDs.
- Docker containers running with `docker compose ps`.
