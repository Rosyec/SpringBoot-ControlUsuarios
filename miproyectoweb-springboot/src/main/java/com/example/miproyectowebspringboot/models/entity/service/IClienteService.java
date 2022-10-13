package com.example.miproyectowebspringboot.models.entity.service;

import java.util.List;

import com.example.miproyectowebspringboot.models.entity.Cliente;

public interface IClienteService {
    public List<Cliente> buscarTodos();
    public Cliente buscarPorId(Cliente cliente);
    public void guardar(Cliente cliente);
    public void eliminar(Cliente cliente);
}
