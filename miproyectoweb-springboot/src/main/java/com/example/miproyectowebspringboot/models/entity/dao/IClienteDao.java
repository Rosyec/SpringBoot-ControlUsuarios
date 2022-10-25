package com.example.miproyectowebspringboot.models.entity.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.miproyectowebspringboot.models.entity.Cliente;

// public interface IClienteDao extends CrudRepository<Cliente, Long>{
    
// }

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.facturas f WHERE c.id = ?1")
    public Cliente fetchByIdWithFacturas(Long id);
}