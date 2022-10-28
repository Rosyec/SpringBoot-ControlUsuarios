package com.example.miproyectowebspringboot.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.example.miproyectowebspringboot.models.entity.Cliente;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView{

    @SuppressWarnings("unchecked")
    @Override
    protected Object filterModel(Map<String, Object> model) {

        model.remove("titulo");

        model.remove("page");

        Page<Cliente> clientes = (Page<Cliente>) model.get("cliente");

        model.remove("cliente");

        model.put("clienteList", clientes.getContent());
        
        return super.filterModel(model);
    }
    
}
