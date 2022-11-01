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
import java.sql.Date;
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
@RequestMapping("campania")
public class CampaniaControlador {
    
    @Autowired
    private PosteoServicio posteoServicio;
    
    @Autowired
    private CampaniaServicio campaniaServicio;
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, ModelMap modelo) {

        Optional<Campania> campanias = campaniaServicio.findById(id);
        modelo.addAttribute("campanias", campanias.get());
        return "detail.html";
    }
    
    @GetMapping("/borrado/{id}")
    public String borrado(@PathVariable Long id, LocalDate fecha, ModelMap modelo){
        
        campaniaServicio.borrar(id, fecha);
        modelo.put("exito", "la campania se eliminó correctamente");
        
        
        return "redirect:/"; 
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo){
        
        Optional<Campania> campania = campaniaServicio.findById(id);

        
        modelo.addAttribute("campania", campania.get());
        modelo.addAttribute("fechaFin", campania.get().getFechaFin());
        modelo.addAttribute("titulo", campania.get().getTitulo());
        modelo.addAttribute("descripcion", campania.get().getDescription());
        
        //modelo.addAttribute("file", posteos.get().getImagen());
        
        return "modificar_form.html"; 
    }
    
    @GetMapping("publicarCampania")
    public String publicar(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();

        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        
        modelo.addAttribute("campanias", campanias);
        return "campanias.html";
    }
    
    @PostMapping("")
    public String index(@RequestParam("titulo") String titulo,
            @RequestParam("description") String description,
            @RequestParam("fechaFin") Date fechaFin, 
            @RequestParam("file") MultipartFile imagen, 
            ModelMap modelo) {
        try {
            campaniaServicio.crearCampania(titulo, description, imagen, fechaFin);

            modelo.put("exito", "la actividad se cargo correctamente");


            return "redirect:/admin/dashboard";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("description", description);
            return "publicarCampania.html";
        }
    }
    
    @GetMapping("/{id}")
    public String campania(@PathVariable Long id, ModelMap modelo){
        
        List<Posteo> posteos = posteoServicio.listarPosteosPorCampanias(id);
        Campania campania = campaniaServicio.findById(id).get();
        modelo.addAttribute("posteos", posteos);
        modelo.addAttribute("campania", campania);
        
        
      
        return "vistaPosteos.html"; 
    }
}
