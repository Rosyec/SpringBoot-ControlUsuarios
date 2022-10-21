package com.example.miproyectowebspringboot.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.miproyectowebspringboot.controllers.paginador.PageRender;
import com.example.miproyectowebspringboot.models.entity.Cliente;
import com.example.miproyectowebspringboot.models.entity.service.IClienteService;

@Controller
@RequestMapping("/app")
@SessionAttributes("cliente") // En lugar del input hidden mejor guardamos el cliente en la Session
public class ClienteController {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model) {
        // Inicio Implementacion de un paginador
        Pageable pageRequest = PageRequest.of(page, 6);// size indica el número de elementos por página
        Page<Cliente> clientes = this.clienteService.buscarTodos(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/app/listar", clientes);
        model.addAttribute("page", pageRender);
        // Fin Implementacion de un paginador
        // ****************************************************************** */

        model.addAttribute("titulo", "Listado clientes");
        model.addAttribute("cliente", clientes);
        // model.addAttribute("cliente", this.clienteService.buscarTodos());
        return "listar";
    }

    @GetMapping("/form")
    public String formulario(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("titulo", "Formulario cliente");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
            @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario cliente");
            return "form";
        }

        if (!foto.isEmpty()) {
            String nombreUnico = UUID.randomUUID().toString().concat("_").concat(foto.getOriginalFilename()).trim();
            Path rootPath = Paths.get("uploads").resolve(nombreUnico);
            Path rootPathAbsolute = rootPath.toAbsolutePath();
            LOG.info("rootPath: " + rootPath);
            LOG.info("rootPathAbsolute: " + rootPathAbsolute);
            
            try {
                Files.copy(foto.getInputStream(), rootPathAbsolute);
                flash.addFlashAttribute("info", "Has subido correctamente " + foto.getOriginalFilename());
                cliente.setFoto(nombreUnico);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente actualizado exitosamente!"
                : "Cliente creado exitosamente!";
        this.clienteService.guardar(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/app/listar";
    }

    @RequestMapping(value = "/form/{id}", method = { RequestMethod.POST, RequestMethod.GET })
    public String actualizar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = null;
        if (id > 0) {
            cliente = this.clienteService.buscarPorId(new Cliente(id));
            if (cliente == null) {
                flash.addFlashAttribute("error", "El cliente no existe!");
                return "redirect:/app/listar";
            }
        } else {
            flash.addFlashAttribute("error", "No fue posible encontrar el cliente con Id = 0!");
            return "redirect:/app/listar";
        }
        model.addAttribute("titulo", "Actualizar Cliente");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @GetMapping(value = "/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            this.clienteService.eliminar(new Cliente(id));
            flash.addFlashAttribute("success", "Cliente eliminado exitosamente!");
        }
        return "redirect:/app/listar";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.buscarPorId(new Cliente(id));
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/listar";
        } 
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){
        Path pathFoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
        Resource recurso = null;
        try {
            recurso = new UrlResource(pathFoto.toUri());
            if (!recurso.exists() || !recurso.isReadable()) {
                throw new RuntimeException("Error: No se puede cargar la foto: " + pathFoto.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
        .body(recurso);
    }

}
