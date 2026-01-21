package com.yui.clearops.controllers;

import com.yui.clearops.model.dto.SystemMetricsDTO;
import com.yui.clearops.services.MonitoramentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/monitoramento")
public class MonitoramentoController {

    private final MonitoramentoService monitoramentoService;

    public MonitoramentoController(MonitoramentoService monitoramentoService) {
        this.monitoramentoService = monitoramentoService;
    }

    @GetMapping
    public String monitorar(Model model) {
        addMetricsToModel(model, monitoramentoService.getMetrics());
        return "monitoramento/monitoramento";
    }

    // Rota que atualiza apenas os cards (HTMX)
    @GetMapping("/metrics")
    public String getMetricsFragment(Model model, @RequestHeader(value = "HX-Request", required = false) boolean isHtmx) {
        addMetricsToModel(model, monitoramentoService.getMetrics());
        if (isHtmx) {
            // Retorna EXATAMENTE o fragmento que o HTMX espera substituir
            return "monitoramento/monitoramento :: #metrics-content";
        }
        return "monitoramento/monitoramento";
    }

    // Modal de Detalhes
    @GetMapping("/offboarding-detalhe")
    public String offboardingDetalhe(@RequestParam int pid,
                                     @RequestParam String name,
                                     @RequestParam double cpu,
                                     @RequestParam double ram,
                                     Model model) {
        model.addAttribute("pid", pid);
        model.addAttribute("name", name);
        model.addAttribute("cpuLoad", cpu);
        model.addAttribute("ramUsage", ram);
        model.addAttribute("impactScore", (cpu * 0.7) + (ram / 1024 * 0.3));
        return "monitoramento/offboarding-card"; // Certifique-se que o HTML do card está nesta pasta
    }

    // Ação Real de Kill
    @PostMapping("/kill")
    public String killProcess(@RequestParam int pid) {
        monitoramentoService.killProcess(pid);
        return "redirect:/monitoramento";
    }

    private void addMetricsToModel(Model model, SystemMetricsDTO m) {
        model.addAttribute("cpuUsage", m.cpuUsage());
        model.addAttribute("ramUsage", m.ramUsage());
        model.addAttribute("ramUsedGb", m.ramUsedGb());
        model.addAttribute("ramTotalGb", m.ramTotalGb());
        model.addAttribute("cpuTemp", m.cpuTemp());
        model.addAttribute("osName", m.osName());
        model.addAttribute("processes", m.processes());
        model.addAttribute("systemUptime", m.uptime());
        model.addAttribute("threadCount", m.threadCount());
    }
}