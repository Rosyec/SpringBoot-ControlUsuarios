package com.example.miproyectowebspringboot.models.entity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.miproyectowebspringboot.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
    
    // @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %?1%")
    // public List<Producto> findByNombre(String term);

    public List<Producto> findByNombreLikeIgnoreCase(String nombre);
}
