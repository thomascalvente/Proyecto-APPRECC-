package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.enumeraciones.Rol;
import com.egg.AppRECC.servicios.PosteoServicio;
import com.egg.AppRECC.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    
}
