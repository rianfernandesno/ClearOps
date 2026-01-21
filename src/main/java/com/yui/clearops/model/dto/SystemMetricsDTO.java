package com.yui.clearops.model.dto;

import java.util.List;

public record SystemMetricsDTO(
        double cpuUsage,
        double ramUsage,
        double ramUsedGb,
        double ramTotalGb,
        double cpuTemp,
        String osName,
        String uptime,
        int threadCount,
        List<ProcessMetricsDTO> processes
) {}