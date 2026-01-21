package com.yui.clearops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerBase {

    @GetMapping("/")
    public String index(){
        return "redirect:/gestao";
    }
}
