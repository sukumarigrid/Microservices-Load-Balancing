package org.example.consumer;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConsumerController {

    private final DependencyClient dependencyClient;

    ConsumerController(DependencyClient dependencyClient) {
        this.dependencyClient = dependencyClient;
    }

    @GetMapping("/api/dependency-message")
    ConsumerResponse dependencyMessage() {
        return new ConsumerResponse("Resolved dependency-service through Eureka", dependencyClient.getMessage(),
                Instant.now());
    }

    record ConsumerResponse(String message, DependencyMessage dependency, Instant timestamp) {
    }
}
