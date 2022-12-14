/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Campania;
import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.CampaniaServicio;
import com.egg.AppRECC.servicios.PosteoServicio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("posteos")
public class PosteoControlador {
    
    @Autowired
    private PosteoServicio posteoServicio;
    
    @Autowired
    private CampaniaServicio campaniaServicio;
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, ModelMap modelo) {

        Optional<Posteo> posteos = posteoServicio.findById(id);
        modelo.addAttribute("posteos", posteos.get());
        return "detail.html";
    }
    
    @GetMapping("/borrado/{id}")
    public String borrado(@PathVariable Long id, LocalDate fecha, ModelMap modelo){
        
        posteoServicio.borrar(id, fecha);
        modelo.put("exito", "el posteo se eliminĂ³ correctamente");
        
        
        return "redirect:/"; 
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo){
        
        Optional<Posteo> posteos = posteoServicio.findById(id);
        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        
        modelo.addAttribute("posteos", posteos.get());
        modelo.addAttribute("titulo", posteos.get().getTitulo());
        modelo.addAttribute("Cuerpo", posteos.get().getCuerpo());
        modelo.addAttribute("campanias", campanias);
        //modelo.addAttribute("file", posteos.get().getImagen());
        
        return "modificar_form.html"; 
    }
    
     @GetMapping("/publicarPosteos/{id}")
    public String publicarPosteos(@PathVariable Long id, ModelMap modelo) { //localhost:8080/
        
        Campania campania = campaniaServicio.findById(id).get();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();

        
        modelo.addAttribute("campania", campania);
        modelo.addAttribute("posteos", posteos);
        return "publicar.html";
    }
    
    @PostMapping("")
    public String index(@RequestParam("id") Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("cuerpo") String cuerpo,
            @RequestParam("file") MultipartFile imagen, 
            ModelMap modelo) {
        try {
            posteoServicio.crearPosteo(titulo, cuerpo, imagen, id);

            modelo.put("exito", "la actividad se cargo correctamente");


            return "redirect:/admin/dashboard";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("cuerpo", cuerpo);
            return "publicar.html";
        }
    }
}
