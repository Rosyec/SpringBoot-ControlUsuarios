package com.example.miproyectowebspringboot.models.entity.dao;

import java.util.List;

import com.example.miproyectowebspringboot.models.entity.Cliente;

public interface IClienteDao {
    public List<Cliente> findAll();
    public Cliente findById(Cliente cliente);
    public void save(Cliente cliente);
    public void delete(Cliente cliente);
}
