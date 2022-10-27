package com.example.miproyectowebspringboot.controllers;

import java.util.Collection;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.example.miproyectowebspringboot.models.entity.service.IUploadService;

@Controller
@RequestMapping(value = {"/app"})
@SessionAttributes("cliente") // En lugar del input hidden mejor guardamos el cliente en la Session
public class ClienteController {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteController.class);
    private static final String UPLOADS_FOLDER = "uploads";

    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;

    @Autowired
    private IUploadService uploadService;

    @GetMapping(value = {"/listar", "/" , ""})
    public String listar(@RequestParam(name = "page", defaultValue = "0") Integer page, Model model, Authentication authentication) {

        if (authentication != null) {
            LOG.info("Hola usuario ".concat(authentication.getName()).concat(", te has autenticado correctamente"));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (hasRole("ROLE_ADMIN")) {
            LOG.info("Hola ".concat(auth.getName()).concat(" , tienes acceso"));
        }else{
            LOG.info("Hola ".concat(auth.getName()).concat(" , NO tienes acceso")); 
        }

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
        model.addAttribute("titulo", "Crear cliente");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
            @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Crear cliente");
            return "form";
        }

        if (!foto.isEmpty()) {
            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
                    && cliente.getFoto().length() > 0) {
                uploadService.delete(cliente.getFoto());
            }
            String nombreUnico = null;
            nombreUnico = uploadService.copy(foto);
            flash.addFlashAttribute("info", "Has subido correctamente " + foto.getOriginalFilename());
            cliente.setFoto(nombreUnico);
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
            Cliente cliente = clienteService.buscarPorId(new Cliente(id));
            this.clienteService.eliminar(new Cliente(id));
            flash.addFlashAttribute("success", "Cliente eliminado exitosamente!");

            if (uploadService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito");
            }
        }
        return "redirect:/app/listar";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
        // Cliente cliente = clienteService.buscarPorId(new Cliente(id));
        Cliente cliente = clienteService.fetchByIdWithFacturas(new Cliente(id));
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/app/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
        Resource recurso = uploadService.load(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    private Boolean hasRole(String role){
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();
        if (auth == null) {
            return false;
        }
        
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(role));

        // for (GrantedAuthority grantedAuthority : authorities) {
        //     if (role.equals(grantedAuthority.getAuthority())) {
        //         LOG.info("Hola usuaurio ".concat(auth.getName()).concat(" , tu role es ").concat(grantedAuthority.getAuthority()));
        //         return true;
        //     }
        // }

        // return false;
    }

}
