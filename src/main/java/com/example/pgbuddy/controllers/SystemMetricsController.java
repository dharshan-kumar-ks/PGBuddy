package com.example.pgbuddy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemMetricsController {

    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // Memory
        long jvmMemoryUsed = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        long jvmMemoryMax = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();
        metrics.put("jvmMemoryUsedMB", jvmMemoryUsed / (1024.0 * 1024.0)); // Convert to MB
        metrics.put("jvmMemoryMaxGB", jvmMemoryMax / (1024.0 * 1024.0 * 1024.0)); // Convert to GB

        // CPU
        double systemCpuLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        metrics.put("systemCpuLoadPercentage", (systemCpuLoad / availableProcessors) * 100); // Convert to percentage

        return metrics;
    }
}

/*
{
    "systemCpuLoadPercentage": 42.900390625, -> 42.9% of the CPU's capacity is currently in use
    "jvmMemoryUsedMB": 84.92332458496094, -> 84.92 MB of memory
    "jvmMemoryMaxGB": 4.0 -> JVM can use up to 4 GB of memory for its heap
}
*/
