package org.example.consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class DependencyClient {

    private static final String DEPENDENCY_URL = "http://dependency-service/api/message";

    private final RestTemplate restTemplate;

    DependencyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    DependencyMessage getMessage() {
        return restTemplate.getForObject(DEPENDENCY_URL, DependencyMessage.class);
    }
}
