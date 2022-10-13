package com.example.miproyectowebspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.dao.IClienteDao;

@Controller
@RequestMapping("/app")
public class ClienteController {

    @Autowired
    @Qualifier("clienteDaoJPA")
    private IClienteDao iClienteDao;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("cliente", iClienteDao.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String formulario(Model model){
        Cliente cliente = new Cliente();
        model.addAttribute("titulo", "Formulario de Clientes");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @PostMapping("/form")
    public String guardar(Cliente cliente){
        iClienteDao.save(cliente);
        return "redirect:/app/listar";
    }
    
}
