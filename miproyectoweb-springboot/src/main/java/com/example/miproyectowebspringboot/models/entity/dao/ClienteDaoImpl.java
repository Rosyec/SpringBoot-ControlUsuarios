package com.example.miproyectowebspringboot.models.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.miproyectowebspringboot.models.entity.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.example.miproyectowebspringboot.models.entity.dao.IClienteDao#findAll()
     */
    @Override
    @Transactional(readOnly = true) // Indica que es de solo lectura
    public List<Cliente> findAll() {
        // return em.createQuery("from Cliente").getResultList();
        return em.createNamedQuery("Cliente.findAll", Cliente.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        if (cliente.getId() != null && cliente.getId() > 0) {
            em.merge(cliente);
        }else{
            em.persist(cliente);
        }
    }

    @Override
    public void delete(Cliente cliente) {
        em.remove(em.merge(cliente));

    }

    @Override
    public Cliente findById(Cliente cliente) {
        return (Cliente) em.createNamedQuery("Cliente.findById", Cliente.class)
                .setParameter("id", cliente.getId())
                .getSingleResult();
    }

}
