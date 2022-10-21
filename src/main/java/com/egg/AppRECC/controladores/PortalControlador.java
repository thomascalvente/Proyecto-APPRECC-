/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.PosteoServicio;
import com.egg.AppRECC.servicios.UsuarioServicio;
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

    @GetMapping("/")
    public String index(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "inicio.html";
    }

    @GetMapping("nosotros")
    public String nosotros(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "nosotros.html";
    }

    @GetMapping("perfil")
    public String perfil(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "perfil.html";
    }

    @GetMapping("publicar")
    public String publicar(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "publicar.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) { //localhost:8080/   
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos");
        }
        List<Posteo> posteos = posteoServicio.listarPosteos();
        modelo.addAttribute("posteos", posteos);

        return "login.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "register.html";
    }

    @PostMapping("/")
    public String Index(@RequestParam("titulo") String titulo,
            @RequestParam("cuerpo") String cuerpo, @RequestParam("file") MultipartFile imagen, ModelMap modelo) {
        try {
            posteoServicio.crearPosteo(titulo, cuerpo, imagen);

            modelo.put("exito", "la actividad se cargo correctamente");

            List<Posteo> posteos = posteoServicio.listarPosteos();

            modelo.addAttribute("posteos", posteos);

            return "redirect:/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "publicar.html";
        }
    }

    @PostMapping("/modificado")
    public String modificado(@RequestParam("id") Long id, @RequestParam("titulo") String titulo,
            @RequestParam("cuerpo") String cuerpo, @RequestParam("file") MultipartFile imagen, LocalDate fecha, ModelMap modelo) {
        posteoServicio.actualizar(id, titulo, cuerpo, imagen, fecha);
        modelo.put("exito", "la actividad se cargo correctamente");
        List<Posteo> posteos = posteoServicio.listarPosteos();
        modelo.addAttribute("posteos", posteos);
        return "redirect:/";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            ModelMap modelo
    ) {
        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2);
            modelo.put("exito", "El usuario se creo correctamente");
            return "redirect:/";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "register.html";
        }
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam("frase") String frase, ModelMap modelo) {
        List<Posteo> posteos = posteoServicio.buscarPosteos(frase);
        modelo.addAttribute("posteos", posteos);
        return "buscarPosteos.html";
    }
}
