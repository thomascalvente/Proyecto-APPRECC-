/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.servicios;

import com.egg.AppRECC.entidades.Posteo;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.repositorios.PosteoRepositorio;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import net.iharder.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PC
 */
@Service
public class PosteoServicio {
    
    @Autowired
    private PosteoRepositorio posteorepositorio;
    
    @Transactional
    public void crearPosteo(String titulo, String cuerpo, MultipartFile imagen, Long id_campania) throws MiException{
        validar(titulo, cuerpo);
        
        Posteo posteo = new Posteo();
        
        if (!imagen.isEmpty()) {
            try {
                posteo.setImagen(Base64.encodeBytes(imagen.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        posteo.setTitulo(titulo);
        posteo.setCuerpo(cuerpo);
        posteo.setId_campania(id_campania);
        posteo.setFecha(LocalDate.now());
        
        posteorepositorio.save(posteo);
    }
    
    
    public List<Posteo> listarPosteos() {

        List<Posteo> posteo = new ArrayList();

        posteo = posteorepositorio.findAll();
        

        return posteo;
    }
    
    
    public Optional<Posteo> findById(Long id) {
        return posteorepositorio.findById(id);
    }
    
    public List<Posteo> listarPosteosBorrados(){
        
        List<Posteo> posteo = new ArrayList();

        posteo = posteorepositorio.listarposteos();
        
        return posteo;
    }
    
    public List<Posteo> buscarPosteos(String frase){
         List<Posteo> posteos = new ArrayList();
         posteos = posteorepositorio.buscarPosteos(frase);
         
         return posteos;
    }
    
    public List<Posteo> listarPosteosPorCampanias(Long id){
         List<Posteo> posteos = new ArrayList();
         posteos = posteorepositorio.mostrarPosteosXCampania(id);
         
         return posteos;
    }
    
    @Transactional
    public void borrar(Long id, LocalDate fecha){
        Optional<Posteo> respuesta = posteorepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Posteo posteo = respuesta.get();
            
            posteo.setFecha(fecha.now());
            posteo.setBorrado(true);
            posteorepositorio.save(posteo);
        }   
    }
    
    @Transactional
    public void actualizar(Long id, String titulo, String cuerpo, MultipartFile imagen, Long id_campania, LocalDate fecha) throws MiException{
        validar(titulo, cuerpo);
        Optional<Posteo> respuesta = posteorepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Posteo posteo = respuesta.get();
            posteo.setTitulo(titulo);
            posteo.setCuerpo(cuerpo);
            posteo.setId_campania(id_campania);
            try {
                posteo.setImagen(Base64.encodeBytes(imagen.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            posteo.setFecha(fecha.now());
            posteorepositorio.save(posteo);
        }        
    }
    
    private void validar( String titulo, String cuerpo) throws MiException {

        

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("el cuerpo de la actividad no puede ser nulo o estar vacio");
        }

    }
}
