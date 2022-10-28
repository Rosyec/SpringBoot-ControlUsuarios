package com.example.miproyectowebspringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping(value = {"/", "", "/index", "/home"})
    public String index(){
        return "redirect:/app/listar";
    }
}
