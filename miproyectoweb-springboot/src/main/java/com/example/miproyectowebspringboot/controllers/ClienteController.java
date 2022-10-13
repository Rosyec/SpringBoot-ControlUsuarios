package com.example.miproyectowebspringboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.dao.IClienteDao;
import com.example.miproyectowebspringboot.models.entity.service.IClienteService;

@Controller
@RequestMapping("/app")
@SessionAttributes("cliente")//En lugar del input hidden mejor guardamos el cliente en la Session
public class ClienteController {

    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("cliente", clienteService.buscarTodos());
        return "listar";
    }

    @GetMapping("/form")
    public String formulario(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("titulo", "Formulario de Clientes");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Clientes");
            return "form";
        }
        clienteService.guardar(cliente);
        status.setComplete();
        return "redirect:/app/listar";
    }

    @RequestMapping(value = "/form/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public String actualizar(@PathVariable Long id, Model model) {
        Cliente cliente = null;
        if (id > 0) {
            cliente = clienteService.buscarPorId(new Cliente(id));
        } else {
            return "redirect:/app/listar";
        }
        model.addAttribute("titulo", "Actualizar Cliente");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String delete(@PathVariable  Long id){
        if (id > 0) {
            this.clienteService.eliminar(new Cliente(id));
        }
        return "redirect:/app/listar";
    }

}
