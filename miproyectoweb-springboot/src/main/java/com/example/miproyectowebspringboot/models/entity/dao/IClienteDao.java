package com.example.miproyectowebspringboot.models.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.miproyectowebspringboot.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
    
}
