package com.example.miproyectowebspringboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.Factura;
import com.example.miproyectowebspringboot.models.entity.ItemFactura;
import com.example.miproyectowebspringboot.models.entity.Producto;
import com.example.miproyectowebspringboot.models.entity.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    IClienteService clienteService;

    @GetMapping("/form/{id}")
    public String crear(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.buscarPorId(new Cliente(id));
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/listar";
        }
        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear factura");
        return "factura/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
        return this.clienteService.buscarPorNombre(term);
    }

    @PostMapping("/form")
    public String guardarFactura(@Valid Factura factura, BindingResult result, Model model,
            @RequestParam(name = "item_id[]", required = false) Long[] itemId,
            @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
            RedirectAttributes flash, SessionStatus status) {
                if (result.hasErrors()) {
                    model.addAttribute("titulo", "Crear factura");
                    return "/factura/form";
                }

                if (itemId == null || itemId.length == 0) {
                    model.addAttribute("titulo", "Crear factura");
                    model.addAttribute("error", "La factura debe contener lineas!");
                    return "/factura/form";
                }

                for (int i = 0; i < itemId.length; i++) {
                    Producto producto = clienteService.buscarProductoPorId(new Producto(itemId[i]));
                    ItemFactura itemFactura = new ItemFactura();
                    itemFactura.setCantidad(cantidad[i]);
                    itemFactura.setProducto(producto);
                    factura.agregarItemFactura(itemFactura);
                }
                clienteService.guardarFactura(factura);
                status.setComplete();
                flash.addFlashAttribute("success", "Factura creada exitosamente!");
        return "redirect:/app/ver/" + factura.getCliente().getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash){
        Factura factura = clienteService.buscarFacturaPorId(new Factura(id));
        if (factura == null) {
            flash.addFlashAttribute("error", "La factura no existe");
            return "redirect:/app/listar";
        }
        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
        return "factura/ver";
    }

}
