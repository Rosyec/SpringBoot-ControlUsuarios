package com.example.miproyectowebspringboot.models.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.miproyectowebspringboot.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
    
}
