
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.controladores;

import com.egg.AppRECC.entidades.Campania;
import com.egg.AppRECC.entidades.Comentario;
import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.enumeraciones.Rol;
import com.egg.AppRECC.excepciones.MiException;

import com.egg.AppRECC.servicios.CampaniaServicio;
import com.egg.AppRECC.servicios.ComentarioServicio;

import com.egg.AppRECC.servicios.PosteoServicio;
import com.egg.AppRECC.servicios.UsuarioServicio;
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

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("campanias", campanias);

        return "inicio.html";
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "listarUsuarios.html";
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

    @GetMapping("/listarPosteos")
    public String listarPosteos(ModelMap modelo) {
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);
        return "listarPosteos.html";
    }

    @GetMapping("/listarCampanias")
    public String listarCampanias(ModelMap modelo) {
        List<Campania> campania = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("campania", campania);
        return "listarCampania.html";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam("frase") String frase, ModelMap modelo) {
        List<Posteo> posteos = posteoServicio.buscarPosteos(frase);
        modelo.addAttribute("posteos", posteos);
        return "buscarPosteos.html";
    }

    @PostMapping("/buscarCampanias")
    public String buscarCapanias(@RequestParam("frase") String frase, ModelMap modelo) {
        List<Campania> campanias = campaniaServicio.buscarCampania(frase, "titulo");
        modelo.addAttribute("campanias", campanias);
        return "buscarCampanias.html";
    }

    @GetMapping("perfil")
    public String perfil(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        modelo.addAttribute("posteos", posteos);

        return "perfil.html";
    }

    @GetMapping("/campania/{id}")
    public String campania(@PathVariable Long id, ModelMap modelo) {

        List<Posteo> posteos = posteoServicio.listarPosteosPorCampanias(id);
        Campania campania = campaniaServicio.findById(id).get();
        modelo.addAttribute("posteos", posteos);
        modelo.addAttribute("campania", campania);

        return "vistaPosteos.html";
    }

    @GetMapping("/posteos/detalle/{id}")
    public String detalle(@PathVariable Long id, ModelMap modelo) {

        List<Comentario> comentarios = comentarioServicio.listarComentariosPorIdPosteos(id);
        modelo.addAttribute("comentarios", comentarios);
        Optional<Posteo> posteos = posteoServicio.findById(id);
        modelo.addAttribute("posteos", posteos.get());
        return "detail.html";
    }

    @GetMapping("/posteos/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {

        Optional<Posteo> posteos = posteoServicio.findById(id);
        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();

        modelo.addAttribute("posteos", posteos.get());
        modelo.addAttribute("titulo", posteos.get().getTitulo());
        modelo.addAttribute("Cuerpo", posteos.get().getCuerpo());
        modelo.addAttribute("campanias", campanias);
        //modelo.addAttribute("file", posteos.get().getImagen());

        return "modificar_form.html";
    }

    @GetMapping("/posteos/borrado/{id}")
    public String borrado(@PathVariable Long id, LocalDate fecha, ModelMap modelo) {

        posteoServicio.borrar(id, fecha);
        modelo.put("exito", "el posteo se eliminó correctamente");

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/posteos/publicarPosteos/{id}")
    public String publicarPosteos(@PathVariable Long id, ModelMap modelo) { //localhost:8080/

        Campania campania = campaniaServicio.findById(id).get();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();

        modelo.addAttribute("campania", campania);
        modelo.addAttribute("posteos", posteos);
        return "publicar.html";
    }

    @PostMapping("/posteos")
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

    @PostMapping("/modificado")
    public String modificado(@RequestParam("id") Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("cuerpo") String cuerpo,
            @RequestParam(required = false, name = "file") MultipartFile imagen,
            LocalDate fecha,
            ModelMap modelo) throws MiException {
        try {
            posteoServicio.actualizar(id, titulo, cuerpo, imagen, id, fecha);
            modelo.put("exito", "la actividad se cargo correctamente");
            List<Posteo> posteos = posteoServicio.listarPosteos();
            modelo.addAttribute("posteos", posteos);
            return "redirect:/admin/dashboard";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.addAttribute("cuerpo", cuerpo);
            return "modificar_form.html";
        }

    }

    @GetMapping("/publicar")
    public String publicar(ModelMap modelo) { //localhost:8080/

        //List<Posteo> posteos = posteoServicio.listarPosteos();
        List<Posteo> posteos = posteoServicio.listarPosteosBorrados();
        List<Campania> campanias = campaniaServicio.listarCampaniasBorradas();
        modelo.addAttribute("posteos", posteos);
        modelo.addAttribute("campanias", campanias);
        return "publicarCampania.html";
    }

    @PostMapping("/campania")
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

    @PostMapping("/comentario")
    public String comentario(@RequestParam("id") Long id,
            @RequestParam("cuerpo") String cuerpo,
            ModelMap modelo) throws MiException {
        try {
            comentarioServicio.crearComentario(cuerpo, id);
            modelo.put("exito", "El comentario se creo correctamente");
            List<Comentario> comentarios = comentarioServicio.listarComentariosPorIdPosteos(id);
            modelo.addAttribute("comentarios", comentarios);
            return "redirect:/admin/dashboard";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.addAttribute("cuerpo", cuerpo);
            return "detail.html";
        }

    }
}
