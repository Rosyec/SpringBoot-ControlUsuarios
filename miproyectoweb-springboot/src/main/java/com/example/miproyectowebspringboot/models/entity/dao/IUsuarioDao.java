package com.example.miproyectowebspringboot.models.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.miproyectowebspringboot.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
    public Usuario findByUsername(String username);
}
