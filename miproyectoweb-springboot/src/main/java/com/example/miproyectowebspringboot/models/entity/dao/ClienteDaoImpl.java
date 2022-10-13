package com.example.miproyectowebspringboot.models.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.miproyectowebspringboot.models.entity.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)//Indica que es de solo lectura
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        em.persist(cliente);
        
    }
    
}
