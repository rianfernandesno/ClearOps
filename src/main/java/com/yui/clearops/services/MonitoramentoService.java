package com.yui.clearops.services;

import com.yui.clearops.model.dto.ProcessMetricsDTO;
import com.yui.clearops.model.dto.SystemMetricsDTO;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoramentoService {
    private final SystemInfo si = new SystemInfo();

    public SystemMetricsDTO getMetrics() {
        var hal = si.getHardware();
        var os = si.getOperatingSystem();
        var cpu = hal.getProcessor();
        var memory = hal.getMemory();

        long[] prevTicks = cpu.getSystemCpuLoadTicks();
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;

        double totalRam = memory.getTotal() / 1024.0 / 1024.0 / 1024.0;
        double availableRam = memory.getAvailable() / 1024.0 / 1024.0 / 1024.0;
        double usedRam = totalRam - availableRam;

        int logicalProcessorCount = hal.getProcessor().getLogicalProcessorCount();

        List<ProcessMetricsDTO> processList = os.getProcesses(OperatingSystem.ProcessFiltering.ALL_PROCESSES, OperatingSystem.ProcessSorting.CPU_DESC, 10)
                .stream()
                .map(p -> new ProcessMetricsDTO(
                        p.getProcessID(),
                        p.getName(),
                        (100d * p.getProcessCpuLoadCumulative()) / logicalProcessorCount,
                        p.getResidentSetSize() / 1024.0 / 1024.0
                )).collect(Collectors.toList());

        return new SystemMetricsDTO(
                Math.round(cpuLoad),
                Math.round((usedRam / totalRam) * 100),
                Math.round(usedRam * 10.0) / 10.0,
                Math.round(totalRam * 10.0) / 10.0,
                hal.getSensors().getCpuTemperature(),
                os.getFamily() + " " + os.getVersionInfo().getVersion(),
                formatUptime(os.getSystemUptime()),
                os.getThreadCount(),
                processList
        );
    }

    public void killProcess(int pid) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("taskkill", "/F", "/PID", String.valueOf(pid)).start();
            } else {
                new ProcessBuilder("kill", "-9", String.valueOf(pid)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatUptime(long seconds) {
        long d = seconds / 86400;
        long h = (seconds % 86400) / 3600;
        long m = (seconds % 3600) / 60;
        return String.format("%dd %dh %dm", d, h, m);
    }
}