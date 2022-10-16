package com.egg.AppRECC.controladores;

import com.egg.AppRECC.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "user_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("email") String email,
            @RequestParam("password") String password, @RequestParam("password2") String password2, ModelMap modelo){
        
        int retorno = usuarioServicio.crearUsuario(nombre, email, password, password2);
        
        
        if(retorno==-1){
            modelo.put("error", "el proceso lanzo una excepcion");
            
        }else if(retorno==1){
            modelo.put("error", "contrasenia no coincide");
            
            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("email", email);
        }else{
            
            modelo.put("exito", "El usuario se creo correctamente");
            return "redirect:/";
        }
        
        
        return "user_form.html";
    }
    
}
