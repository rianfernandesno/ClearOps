package com.yui.clearops.controllers;

import com.yui.clearops.model.entities.Software;
import com.yui.clearops.services.SoftwareService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/gestao")
public class SoftwareController {

    @Autowired
    private SoftwareService service;

    @GetMapping
    public String start(Model model){

        model.addAttribute("softwares", service.listAll());
        return "software/list";
    }

    @GetMapping("/filtro")
    public String filtrar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String depto,
            @RequestParam(required = false) String status,
            Model model){

        List<Software> resultados = service.buscarComFiltro(nome, depto, status);
        model.addAttribute("softwares", resultados);
        return "software/list :: lista";
    }

    @GetMapping("/novo")
    public String abrirModal(Model model){
        return "software/form";
    }

    @PostMapping("/salvar")
    public String salvar(Software software, Model model){
        service.save(software);
        model.addAttribute("softwares", service.listAll());
        return "software/list :: lista";
    }
}