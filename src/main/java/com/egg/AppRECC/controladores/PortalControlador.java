/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Campania;
import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.CampaniaServicio;
import com.egg.AppRECC.servicios.PosteoServicio;
import com.egg.AppRECC.servicios.UsuarioServicio;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private PosteoServicio posteoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CampaniaServicio campaniaServicio;

    @GetMapping("/")
    public String intro(ModelMap modelo) { //localhost:8080/
        return "introduccion.html";
    }

    @GetMapping("/logueado")
    public String index(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("campanias", campanias);

        return "inicio.html";
    }

    @GetMapping("nosotros")
    public String nosotros(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "nosotros.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) { //localhost:8080/   
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos");
        }

        return "login.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "register.html";
    }


    @PostMapping("/registro")
    public String registro(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            @RequestParam("file") MultipartFile imagen,
            ModelMap modelo
    ) throws IOException {
        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2, imagen);
            modelo.put("exito", "El usuario se creo correctamente");
            return "redirect:/";

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "register.html";
        }
    }

}
