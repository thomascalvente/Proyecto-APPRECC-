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

   @GetMapping("/register")
    public String crearUsuarioFormulario(Model modelo) {
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "register";
   }
    
    @PostMapping("login")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        servicio.guardarUsuario(usuario);
        return "redirect:/login";

    }
}
