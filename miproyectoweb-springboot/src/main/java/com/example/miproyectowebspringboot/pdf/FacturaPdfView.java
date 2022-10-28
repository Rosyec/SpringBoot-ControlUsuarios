package com.example.miproyectowebspringboot.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.miproyectowebspringboot.models.entity.Factura;
import com.example.miproyectowebspringboot.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Locale locale = localeResolver.resolveLocale(request);

        MessageSourceAccessor traduccion = getMessageSourceAccessor();

        Factura factura = (Factura) model.get("factura");

        PdfPTable tabla = new PdfPTable(1);
        tabla.setSpacingAfter(20);

        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase(traduccion.getMessage("text.pdf.titulo"), new Font(null, 20, Font.BOLD)));
        cell.setBorder(0);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla.addCell(cell);

        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase(traduccion.getMessage("text.cliente.factura.ver.datosCliente"), new Font(null, 13, Font.BOLD)));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setPadding(15f);
        tabla2.addCell(cell);

        tabla2.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        tabla2.addCell(factura.getCliente().getEmail());

        PdfPTable tabla3 = new PdfPTable(1);
        tabla3.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase(traduccion.getMessage("text.cliente.factura.ver.datosFactura"), new Font(null, 13, Font.BOLD)));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setPadding(15f);

        tabla3.addCell(cell);
        tabla3.addCell("ID : " + factura.getId());
        tabla3.addCell(messageSource.getMessage("text.cliente.factura.ver.descripcion", null, locale) + " : " + factura.getDescripcion());
        tabla3.addCell(messageSource.getMessage("text.cliente.factura.ver.fecha", null, locale) + " : " + factura.getCreateAt());

        PdfPTable tabla4 = new PdfPTable(4);
        tabla4.setWidths(new float[]{3.5f, 1, 1, 1});
        tabla4.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.cliente.factura.ver.producto", null, locale), new Font(null, 13, Font.BOLD)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla4.addCell(cell);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.cliente.factura.ver.precio", null, locale), new Font(null, 13, Font.BOLD)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla4.addCell(cell);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.cliente.factura.ver.cantidad", null, locale), new Font(null, 13, Font.BOLD)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla4.addCell(cell);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.cliente.factura.ver.total", null, locale), new Font(null, 13, Font.BOLD)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla4.addCell(cell);

        for (ItemFactura itemFactura : factura.getItems()) {
            cell = new PdfPCell(new Phrase(itemFactura.getProducto().getNombre()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla4.addCell(cell);
            cell = new PdfPCell(new Phrase("$" + itemFactura.getProducto().getPrecio().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla4.addCell(cell);
            cell = new PdfPCell(new Phrase(itemFactura.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla4.addCell(cell);
            cell = new PdfPCell(new Phrase("$" + itemFactura.calcularImporte().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla4.addCell(cell);
        }

        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.cliente.factura.ver.granTotal", null, locale), new Font(null, 13, Font.BOLD)));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla4.addCell(cell);
        cell = new PdfPCell(new Phrase("$" + factura.getTotal().toString()));
        cell.setBackgroundColor(new Color(242, 247, 183));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla4.addCell(cell);

        PdfPTable tabla5 = new PdfPTable(1);
        Image image = Image.getInstance("https://cdn-icons-png.flaticon.com/512/152/152760.png");
        image.scalePercent((float)7.5);
        image.setAlignment(1);
        cell = new PdfPCell(new Phrase(""));
        cell.addElement(image);
        cell.setBorder(0);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tabla5.setWidths(new float[]{0.1f});

        tabla5.addCell(cell);
        cell = new PdfPCell(new Phrase("Hecho con ♥ por Rosyec"));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(0);
        tabla5.addCell(cell);

        cell = new PdfPCell(new Phrase("© 2022 - Todos los derechos reservados"));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(0);

        tabla5.addCell(cell);
        

        document.addTitle("PDF - FACTURA");
        document.add(tabla);
        document.add(tabla2);
        document.add(tabla3);
        document.add(tabla4);
        document.add(tabla5);

        
    }
    
}
