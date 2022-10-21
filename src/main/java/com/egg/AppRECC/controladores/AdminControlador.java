
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.servicios.PosteoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private PosteoServicio posteoServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo){
        
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);
        
        return "inicio.html";
    }
}
