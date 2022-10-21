package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.servicios.UsuarioServicio;
import java.util.List;
import java.util.Optional;
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

  @GetMapping("/listar")
  public String listar(ModelMap modelo) {
    List<Usuario> usuario = usuarioServicio.listar();
    modelo.addAttribute("usuarios", usuario);
    return "listarUsuarios.html";
  }

}
