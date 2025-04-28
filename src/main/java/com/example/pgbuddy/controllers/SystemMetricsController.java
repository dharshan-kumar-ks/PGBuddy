package com.example.pgbuddy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling system metrics-related requests. (using Spring Actuator)
 * Provides an endpoint to retrieve system metrics such as CPU and memory usage.
 */
@RestController
@RequestMapping("/api/system")
public class SystemMetricsController {

    /**
     * Retrieves system metrics including JVM memory usage and system CPU load.
     *
     * @return A map containing the following metrics:
     *         - "jvmMemoryUsedMB": The amount of JVM heap memory currently in use (in MB).
     *         - "jvmMemoryMaxGB": The maximum amount of JVM heap memory available (in GB).
     *         - "systemCpuLoadPercentage": The percentage of CPU load currently in use.
     */
    // GET method to fetch system metrics
    // This method will return the current CPU and memory usage of this spring boot application
    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>(); // HashMap to store metrics

        // Memory:-
        long jvmMemoryUsed = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed(); // Get used memory
        long jvmMemoryMax = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax(); // Get max memory
        // Convert to MB and GB & store in the map
        metrics.put("jvmMemoryUsedMB", jvmMemoryUsed / (1024.0 * 1024.0)); // Convert to MB
        metrics.put("jvmMemoryMaxGB", jvmMemoryMax / (1024.0 * 1024.0 * 1024.0)); // Convert to GB

        // CPU:-
        double systemCpuLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage(); // Get system CPU load
        int availableProcessors = Runtime.getRuntime().availableProcessors(); // Get available processors
        // Convert to percentage & store in the map
        metrics.put("systemCpuLoadPercentage", (systemCpuLoad / availableProcessors) * 100); // Convert to percentage

        return metrics;
    }
}

/*
Example JSON response:
{
    "systemCpuLoadPercentage": 42.900390625, -> 42.9% of the CPU's capacity is currently in use
    "jvmMemoryUsedMB": 84.92332458496094, -> 84.92 MB of memory
    "jvmMemoryMaxGB": 4.0 -> JVM can use up to 4 GB of memory for its heap
}
*/
