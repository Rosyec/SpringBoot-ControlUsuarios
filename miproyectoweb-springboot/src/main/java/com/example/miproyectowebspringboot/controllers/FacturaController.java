package com.example.miproyectowebspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.Factura;
import com.example.miproyectowebspringboot.models.entity.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    IClienteService clienteService;

    @GetMapping("/form/{id}")
    public String crear(@PathVariable Long id, Model model, RedirectAttributes flash){
        Cliente cliente = clienteService.buscarPorId(new Cliente(id));
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/listar";
        }
        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear factura");
        return "factura/form";
    }
    
}
