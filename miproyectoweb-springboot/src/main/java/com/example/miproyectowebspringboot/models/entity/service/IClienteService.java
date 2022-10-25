package com.example.miproyectowebspringboot.models.entity.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.Factura;
import com.example.miproyectowebspringboot.models.entity.Producto;

public interface IClienteService {
    public List<Cliente> buscarTodos();
    public Page<Cliente> buscarTodos(Pageable pageable);
    public Cliente buscarPorId(Cliente cliente);
    public void guardar(Cliente cliente);
    public void eliminar(Cliente cliente);
    public List<Producto> buscarPorNombre(String termino);
    public void guardarFactura(Factura factura);
    public Producto buscarProductoPorId(Producto producto);
    public Factura buscarFacturaPorId( Factura factura);
    public void eliminarFactura(Factura factura);
    public Factura fetchByIdWithClienteWhitItemFacturaWithProducto(Factura factura);
    public Cliente fetchByIdWithFacturas(Cliente cliente);
}
