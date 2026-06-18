package org.example.dependency;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageController {

    private final String instanceId;
    private final int port;

    MessageController(
            @Value("${eureka.instance.instance-id:${spring.application.name}:${server.port}}") String instanceId,
            @Value("${server.port}") int port) {
        this.instanceId = instanceId;
        this.port = port;
    }

    @GetMapping("/api/message")
    MessageResponse message() {
        return new MessageResponse("Response from dependency-service", instanceId, port, Instant.now());
    }

    record MessageResponse(String message, String instanceId, int port, Instant timestamp) {
    }
}
