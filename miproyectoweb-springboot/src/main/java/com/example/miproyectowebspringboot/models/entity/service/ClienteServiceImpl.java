package com.example.miproyectowebspringboot.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.Factura;
import com.example.miproyectowebspringboot.models.entity.Producto;
import com.example.miproyectowebspringboot.models.entity.dao.IClienteDao;
import com.example.miproyectowebspringboot.models.entity.dao.IFacturaDao;
import com.example.miproyectowebspringboot.models.entity.dao.IProductoDao;

@Service("clienteService")
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    @Autowired
    private IFacturaDao facturaDao;

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

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> buscarTodos(Pageable pageable) {
        return this.clienteDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String termino) {
        return this.productoDao.findByNombreLikeIgnoreCase("%"+ termino + "%");
    }

    @Override
    @Transactional
    public void guardarFactura(Factura factura) {
        this.facturaDao.save(factura);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Producto buscarProductoPorId(Producto producto) {
        return this.productoDao.findById(producto.getId()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura buscarFacturaPorId(Factura factura) {
        return this.facturaDao.findById(factura.getId()).orElse(null);
    }

    @Override
    @Transactional
    public void eliminarFactura(Factura factura) {
        facturaDao.delete(factura);
        
    }

    @Override
    public Factura fetchByIdWithClienteWhitItemFacturaWithProducto(Factura factura) {
        return facturaDao.fetchByIdWithClienteWhitItemFacturaWithProducto(factura.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchByIdWithFacturas(Cliente cliente) {
        return clienteDao.fetchByIdWithFacturas(cliente.getId());
    }

    
    
}
