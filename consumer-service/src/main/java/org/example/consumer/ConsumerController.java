package org.example.consumer;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConsumerController {

    private final DependencyClient dependencyClient;
    private final String loadBalancerAlgorithm;

    ConsumerController(
            DependencyClient dependencyClient,
            @Value("${app.load-balancer.algorithm:round-robin}") String loadBalancerAlgorithm) {
        this.dependencyClient = dependencyClient;
        this.loadBalancerAlgorithm = loadBalancerAlgorithm;
    }

    @GetMapping("/api/dependency-message")
    ConsumerResponse dependencyMessage() {
        return new ConsumerResponse("Resolved dependency-service through Eureka", loadBalancerAlgorithm,
                dependencyClient.getMessage(), Instant.now());
    }

    record ConsumerResponse(String message, String loadBalancerAlgorithm, DependencyMessage dependency,
            Instant timestamp) {
    }
}
