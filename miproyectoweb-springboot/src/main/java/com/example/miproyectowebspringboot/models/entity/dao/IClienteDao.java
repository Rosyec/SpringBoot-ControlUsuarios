package com.example.miproyectowebspringboot.models.entity.dao;

import java.util.List;

import com.example.miproyectowebspringboot.models.entity.Cliente;

public interface IClienteDao {
    public List<Cliente> findAll();
    public void save(Cliente cliente);
}
