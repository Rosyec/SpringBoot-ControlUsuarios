package com.example.miproyectowebspringboot.xlxs;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import com.example.miproyectowebspringboot.models.entity.Factura;
import com.example.miproyectowebspringboot.models.entity.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"Factura.xlsx\"");

        MessageSourceAccessor traduccion = getMessageSourceAccessor();

        Factura factura = (Factura) model.get("factura");

        Sheet sheet = workbook.createSheet("Factura Excel");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        cell.setCellValue(traduccion.getMessage("text.cliente.factura.ver.datosCliente"));

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getEmail().toString());

        sheet.createRow(4).createCell(0).setCellValue(traduccion.getMessage("text.cliente.factura.ver.datosFactura"));
        sheet.createRow(5).createCell(0).setCellValue("ID : " + factura.getId());
        sheet.createRow(6).createCell(0).setCellValue(traduccion.getMessage("text.cliente.factura.ver.descripcion") + " : " + factura.getDescripcion());
        sheet.createRow(7).createCell(0).setCellValue(traduccion.getMessage("text.cliente.factura.ver.fecha") + " : " + factura.getCreateAt());

        CellStyle theadStyle = workbook.createCellStyle();
        theadStyle.setBorderBottom(BorderStyle.MEDIUM);
        theadStyle.setBorderTop(BorderStyle.MEDIUM);
        theadStyle.setBorderLeft(BorderStyle.MEDIUM);
        theadStyle.setBorderRight(BorderStyle.MEDIUM);
        theadStyle.setFillForegroundColor(IndexedColors.AQUA.index);
        theadStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue("Producto");
        header.createCell(1).setCellValue("Precio");
        header.createCell(2).setCellValue("Cantidad");
        header.createCell(3).setCellValue("Total");

        header.getCell(0).setCellStyle(theadStyle);
        header.getCell(1).setCellStyle(theadStyle);
        header.getCell(2).setCellStyle(theadStyle);
        header.getCell(3).setCellStyle(theadStyle);

        Integer rowNum = 10;
        for (ItemFactura itemFactura : factura.getItems()) {
            Row fila = sheet.createRow(rowNum++);
            
            cell = fila.createCell(0);
            cell.setCellValue(itemFactura.getProducto().getNombre());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(1);
            cell.setCellValue(itemFactura.getProducto().getPrecio());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(2);
            cell.setCellValue(itemFactura.getCantidad());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(3);
            cell.setCellValue(itemFactura.calcularImporte());
            cell.setCellStyle(tbodyStyle);

        }

        Row filaTotal = sheet.createRow((rowNum));
        filaTotal.createCell(2).setCellValue("Gran Total: ");
        filaTotal.createCell(3).setCellValue(factura.getTotal());
    }
    
}
