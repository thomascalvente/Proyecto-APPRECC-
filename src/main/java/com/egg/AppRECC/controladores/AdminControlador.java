<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
=======
>>>>>>> ae94473c5c8c48e2dc38a86fb1c7a60211f386d1
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.enumeraciones.Rol;
<<<<<<< HEAD
import com.egg.AppRECC.servicios.CampaniaServicio;
=======
>>>>>>> ae94473c5c8c48e2dc38a86fb1c7a60211f386d1
import com.egg.AppRECC.servicios.PosteoServicio;
import com.egg.AppRECC.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
/**
 *
 * @author PC
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private PosteoServicio posteoServicio;
    
    @Autowired
    private CampaniaServicio campaniaServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo){
        
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);
        
        
        
        return "inicio.html";
    }
    
    
    
  @GetMapping("/listar")
  public String listar(ModelMap modelo) {
    List<Usuario> usuarios = usuarioServicio.listarUsuarios();
    modelo.addAttribute("usuarios", usuarios);
    return "listarUsuarios.html";
  }
  
  @GetMapping("/modificar/{id}/{rol}")
  public String modificar(@PathVariable String id, @PathVariable Rol rol, ModelMap modelo){
      
      usuarioServicio.cambiarRol(id, rol);
      modelo.put("exito", "el rol del usuario se modifico correctamente");
      
      return "redirect:/admin/listar";
  }
  
  @GetMapping("/eliminar/{id}")
  public String eliminar(@PathVariable String id, ModelMap modelo){
      
      usuarioServicio.eliminar(id);
      modelo.put("exito", "el usuario se eliminó correctamente");
      
      return "redirect:/admin/listar"; 
  }
=======
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private PosteoServicio posteoServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "inicio.html";
    }

    @GetMapping("/modificar/{id}/{rol}")
    public String modificar(@PathVariable String id, @PathVariable Rol rol, ModelMap modelo) {

        usuarioServicio.cambiarRol(id, rol);
        modelo.put("exito", "el rol del usuario se modifico correctamente");

        return "redirect:/admin/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) {

        usuarioServicio.eliminar(id);
        modelo.put("exito", "el usuario se eliminó correctamente");

        return "redirect:/admin/listar";
}

    
>>>>>>> ae94473c5c8c48e2dc38a86fb1c7a60211f386d1
}
