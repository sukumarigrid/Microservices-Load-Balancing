package org.example.consumer;

import java.time.Instant;

record DependencyMessage(String message, String instanceId, int port, Instant timestamp) {
}
