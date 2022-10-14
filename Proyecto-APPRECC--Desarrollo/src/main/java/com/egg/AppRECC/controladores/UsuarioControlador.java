package com.egg.AppRECC.controladores;


import org.springframework.stereotype.Controller;
import com.egg.AppRECC.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.egg.AppRECC.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio servicio;

    @GetMapping({"/usuarios","/"})
    public String listarUsuario(Model modelo){
        modelo.addAttribute("usuarios", servicio.listarTodosLosUsuarios());
        return "usuarios";
    }

   @GetMapping("usuarios/register")
    public String crearUsuarioFormulario(Model modelo) {
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "register";
   }
    @GetMapping({"/usuarios/login"})
    public String ingresarUsuario(Model modelo){
        return "login";
    }
    
    @PostMapping("/usuarios")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        servicio.guardarUsuario(usuario);
        return "redirect:/usuarios";

    }
}
