/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.PosteoServicio;
import java.time.LocalDate;
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
    
    @GetMapping("/")
    public String index(ModelMap modelo) { //localhost:8080/
        
        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "nuevo/inicio.html";
    }
    
    @GetMapping("nosotros")
    public String nosotros(ModelMap modelo) { //localhost:8080/
        
        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "nuevo/nosotros.html";
    }
    
    @GetMapping("perfil")
    public String perfil(ModelMap modelo) { //localhost:8080/
        
        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "nuevo/perfil.html";
    }
    
    @GetMapping("publicar")
    public String publicar(ModelMap modelo) { //localhost:8080/
        
        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "nuevo/publicar.html";
    }
    @GetMapping("/login")
    public String login(ModelMap modelo) { //localhost:8080/
        
        List<Posteo> posteos = posteoServicio.listarPosteos();
        modelo.addAttribute("posteos", posteos);

        return "login.html";
    }
    
    @GetMapping("/register")
    public String register(ModelMap modelo) { //localhost:8080/
        
        List<Posteo> posteos = posteoServicio.listarPosteos();
        modelo.addAttribute("posteos", posteos);

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
            return "/nuevo/publicar.html";
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
    

    
}
