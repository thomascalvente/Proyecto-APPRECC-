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
        
        if(logueado.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/dashboard";
        }
        
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);
        
        return "inicio.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        return "register.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, 
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2, 
            @RequestParam("file") MultipartFile imagen, ModelMap modelo) throws IOException{

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
    
}
