package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Campania;
import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.enumeraciones.Rol;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.CampaniaServicio;
import com.egg.AppRECC.servicios.PosteoServicio;
import com.egg.AppRECC.servicios.UsuarioServicio;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/inicio")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CampaniaServicio campaniaServicio;

    @Autowired
    private PosteoServicio posteoServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_GUEST')")
    @GetMapping("")
    public String inicio(HttpSession session, ModelMap modelo) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (logueado.getRol().toString().equals("GUEST")) {
            return "redirect:/guest/inicio";
        }
        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("campanias", campanias);
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "inicio.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "register.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            @RequestParam("file") MultipartFile imagen, ModelMap modelo) throws IOException {

        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2, imagen);
            modelo.put("exito", "El usuario se creo correctamente");
            return "redirect:/";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "user_form";
        }

    }

    @GetMapping("/listarPosteos")
    public String listarPosteos(ModelMap modelo) {
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);
        return "listarPosteos.html";
    }

    @GetMapping("/listarCampanias")
    public String listarCampanias(ModelMap modelo) {
        List<Campania> campania = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("campania", campania);
        return "listarCampania.html";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam("frase") String frase, ModelMap modelo) {
        List<Posteo> posteos = posteoServicio.buscarPosteos(frase);
        modelo.addAttribute("posteos", posteos);
        return "buscarPosteos.html";
    }

    @PostMapping("/buscarCampanias")
    public String buscarCapanias(@RequestParam("frase") String frase, ModelMap modelo) {
        List<Campania> campanias = campaniaServicio.buscarCampania(frase, "titulo");
        modelo.addAttribute("campanias", campanias);
        return "buscarCampanias.html";
    }

    @GetMapping("perfil")
    public String perfil(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "perfil.html";
    }

    @GetMapping("/campania/{id}")
    public String campania(@PathVariable Long id, ModelMap modelo) {

        List<Posteo> posteos = posteoServicio.listarPosteosPorCampanias(id);
        Campania campania = campaniaServicio.findById(id).get();
        modelo.addAttribute("posteos", posteos);
        modelo.addAttribute("campania", campania);

        return "vistaPosteos.html";
    }

    @GetMapping("/posteos/detalle/{id}")
    public String detalle(@PathVariable Long id, ModelMap modelo) {

        Optional<Posteo> posteos = posteoServicio.findById(id);
        modelo.addAttribute("posteos", posteos.get());
        return "detail.html";
    }

    @GetMapping("/publicar")
    public String publicar(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("posteos", posteos);
        modelo.addAttribute("campanias", campanias);
        return "publicarCampania.html";
    }

    @PostMapping("/campania")
    public String index(@RequestParam("titulo") String titulo,
            @RequestParam("description") String description,
            @RequestParam("fechaFin") Date fechaFin,
            @RequestParam("file") MultipartFile imagen,
            ModelMap modelo) {
        try {
            campaniaServicio.crearCampania(titulo, description, imagen, fechaFin);

            modelo.put("exito", "la actividad se cargo correctamente");

            return "redirect:/inicio";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("description", description);
            return "publicarCampania.html";
        }
    }
    
    @GetMapping("/posteos/publicarPosteos/{id}")
    public String publicarPosteos(@PathVariable Long id, ModelMap modelo) { //localhost:8080/
        
        Campania campania = campaniaServicio.findById(id).get();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();

        
        modelo.addAttribute("campania", campania);
        modelo.addAttribute("posteos", posteos);
        return "publicar.html";
    }
    
    @PostMapping("/posteos")
    public String index(@RequestParam("id") Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("cuerpo") String cuerpo,
            @RequestParam("file") MultipartFile imagen, 
            ModelMap modelo) {
        try {
            posteoServicio.crearPosteo(titulo, cuerpo, imagen, id);

            modelo.put("exito", "la actividad se cargo correctamente");


            return "redirect:/inicio";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("cuerpo", cuerpo);
            return "publicar.html";
        }
    }

}
