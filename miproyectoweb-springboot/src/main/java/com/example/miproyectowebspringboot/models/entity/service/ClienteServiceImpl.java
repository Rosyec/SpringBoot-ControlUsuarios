package com.example.miproyectowebspringboot.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.dao.IClienteDao;

@Service("clienteService")
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos() {
        return (List<Cliente>) this.clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Cliente cliente) {
        return this.clienteDao.findById(cliente.getId()).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        this.clienteDao.save(cliente);   
    }

    @Override
    @Transactional
    public void eliminar(Cliente cliente) {
        this.clienteDao.deleteById(cliente.getId());
    }
    
}
