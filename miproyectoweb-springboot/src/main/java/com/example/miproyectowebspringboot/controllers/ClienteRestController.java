package com.example.miproyectowebspringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.service.IClienteService;

@RestController
@RequestMapping("/api-clientes")
public class ClienteRestController {

    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;
    
    @GetMapping(value = {"/listar"})
    public List<Cliente> listar() {
        return clienteService.buscarTodos();
    }

    @GetMapping("/Id/{id}")
    public Cliente buscar(@PathVariable Long id){
        return clienteService.buscarPorId(new Cliente(id));
    }
}
