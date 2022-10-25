/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.servicios;

import com.egg.AppRECC.entidades.Campania;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.repositorios.CampaniaRepositorio;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class CampaniaServicio {
    
    @Autowired
    private CampaniaRepositorio campaniarepositorio;
    
    @Transactional
    public void crearCampania(String titulo, String cuerpo, MultipartFile imagen) throws MiException{
        validar(titulo, cuerpo);
        
        Campania campania = new Campania();
        
        if (!imagen.isEmpty()) {
            try {
                campania.setImagen(Base64.encodeBytes(imagen.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        campania.setTitulo(titulo);
        campania.setCuerpo(cuerpo);
        campania.setFecha(LocalDate.now());
        
        campaniarepositorio.save(campania);
    }
    
    
    public List<Campania> listarCampanias() {

        List<Campania> campania = new ArrayList();

        campania = campaniarepositorio.findAll();
        

        return campania;
    }
    
    
    public Optional<Campania> findById(Long id) {
        return campaniarepositorio.findById(id);
    }
    
    public List<Campania> listarCampaniasBorradas(){
        
        List<Campania> campania = new ArrayList();

        campania = campaniarepositorio.listarCampanias();
        
        return campania;
    }
    
    public List<Campania> buscarCampania(String frase){
         List<Campania> campania = new ArrayList();
         campania = campaniarepositorio.buscarCampanias(frase);
         
         return campania;
    }
    
    @Transactional
    public void borrar(Long id, LocalDate fecha){
        Optional<Campania> respuesta = campaniarepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Campania campania = respuesta.get();
            
            campania.setFecha(fecha.now());
            campania.setBorrado(true);
            campaniarepositorio.save(campania);
        }   
    }
    
    @Transactional
    public void actualizar(Long id, String titulo, String cuerpo, MultipartFile imagen, LocalDate fecha){
        
        Optional<Campania> respuesta = campaniarepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Campania campania = respuesta.get();
            campania.setTitulo(titulo);
            campania.setCuerpo(cuerpo);
            try {
                campania.setImagen(Base64.encodeBytes(imagen.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            campania.setFecha(fecha.now());
            campaniarepositorio.save(campania);
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
