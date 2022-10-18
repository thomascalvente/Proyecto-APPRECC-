package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.UsuarioServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar(){
        return "register.html";
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        List <Usuario> usuario = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuario);
        return "listarUsuarios.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("email") String email,

            @RequestParam("password") String password, @RequestParam("password2") String password2, ModelMap modelo){

        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2);
            modelo.put("exito", "El usuario se creo correctamente");
            return "redirect:/";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());

            return "user_form";

    }
    
    
}
