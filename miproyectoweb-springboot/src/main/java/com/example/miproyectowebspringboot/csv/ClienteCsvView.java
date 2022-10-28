package com.example.miproyectowebspringboot.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.miproyectowebspringboot.models.entity.Cliente;

@Component("listar.csv")
public class ClienteCsvView extends AbstractView {

    private Logger LOG = LoggerFactory.getLogger(ClienteCsvView.class);

    public ClienteCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"Clientes.csv\"");
        response.setContentType(getContentType());

        Page<Cliente> clientes = (Page<Cliente>) model.get("cliente");

        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] header = { "id", "nombre", "apellido", "email", "createAt" };

        beanWriter.writeHeader(header);

        for (Cliente cliente : clientes) {
            LOG.info("CLIENTE: " + cliente.getNombre());
            beanWriter.write(cliente, header);
        }

        beanWriter.close();

    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

}
