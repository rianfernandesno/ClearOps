package com.yui.clearops.controllers;

import com.yui.clearops.services.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String filtrar(Model model){

        return "software/list :: #lista-software";
    }
}
