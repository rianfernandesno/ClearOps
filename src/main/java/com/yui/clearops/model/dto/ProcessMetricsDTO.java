package com.yui.clearops.model.dto;

public record ProcessMetricsDTO(
        int pid,
        String name,
        double cpuUsage,
        double ramUsageMb
) {}
