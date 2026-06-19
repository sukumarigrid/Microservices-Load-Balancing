# Microservices Service Discovery, Load Balancing, and Gateway

This project demonstrates client-side service discovery with Netflix Eureka, Spring Cloud LoadBalancer, and Spring Cloud
Gateway.

## Services

- `eureka-server`: service registry UI on `http://localhost:8761`
- `dependency-service`: sample dependency registered as `DEPENDENCY-SERVICE`
- `consumer-service`: calls `http://dependency-service/api/message` through a load-balanced Eureka client
- `api-gateway`: routes external API traffic on `http://localhost:8088`

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

Call the same API through Spring Cloud Gateway:

```bash
curl http://localhost:8088/api/dependency-message
```

## Change Load Balancing Algorithm

The consumer uses Spring Cloud LoadBalancer. The default algorithm is round-robin:

```yaml
app:
  load-balancer:
    algorithm: round-robin
```

In Docker Compose, the default `consumer-service` algorithm is `round-robin`. To demonstrate random balancing, use the
included override file:

```bash
docker compose -f docker-compose.yml -f docker-compose.random.yml up --build
```

Or recreate only the consumer and gateway after the base stack is already running:

```bash
docker compose -f docker-compose.yml -f docker-compose.random.yml up --build -d consumer-service api-gateway
```

With `round-robin`, repeated responses should alternate evenly between ports `8081` and `8082`. With `random`, repeated
responses should still hit both instances, but the order will not be strictly alternating. The JSON response includes
`loadBalancerAlgorithm` so screenshots show which algorithm is active.

## Run A Second Instance Manually

You can also run another dependency instance outside Docker on a different port:

```bash
mvn -pl dependency-service spring-boot:run \
  -Dspring-boot.run.arguments="--server.port=8091 --eureka.client.service-url.defaultZone=http://localhost:8761/eureka/"
```

Then refresh `http://localhost:8761` and verify a third `DEPENDENCY-SERVICE` instance appears.

## Screenshot Checklist For Review

Attach these screenshots to the merge request:

- Eureka dashboard showing `API-GATEWAY`, `CONSUMER-SERVICE`, and multiple `DEPENDENCY-SERVICE` instances.
- Repeated `curl http://localhost:8080/api/dependency-message` responses showing round-robin behavior.
- Repeated `curl http://localhost:8088/api/dependency-message` responses showing the API Gateway route works.
- Repeated responses after switching `LOAD_BALANCER_ALGORITHM=random`.
- Docker containers running with `docker compose ps`.
