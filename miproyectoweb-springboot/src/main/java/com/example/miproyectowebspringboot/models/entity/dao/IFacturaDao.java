package com.example.miproyectowebspringboot.models.entity.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.miproyectowebspringboot.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
    @Query("SELECT f FROM Factura f JOIN FETCH f.cliente c JOIN FETCH f.items i JOIN FETCH i.producto WHERE f.id = ?1")
    public Factura fetchByIdWithClienteWhitItemFacturaWithProducto(Long id);
}
