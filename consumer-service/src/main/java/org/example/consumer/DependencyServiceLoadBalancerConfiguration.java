package org.example.consumer;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

class DependencyServiceLoadBalancerConfiguration {

    @Bean
    ReactorLoadBalancer<ServiceInstance> dependencyServiceLoadBalancer(
            Environment environment,
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            @Value("${app.load-balancer.algorithm:round-robin}") String algorithm) {

        String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        if ("random".equalsIgnoreCase(algorithm)) {
            return new RandomLoadBalancer(serviceInstanceListSupplierProvider, serviceId);
        }
        return new RoundRobinLoadBalancer(serviceInstanceListSupplierProvider, serviceId);
    }
}
