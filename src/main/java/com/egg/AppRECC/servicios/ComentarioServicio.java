/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.servicios;

import com.egg.AppRECC.entidades.Comentario;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.repositorios.ComentarioRepositorio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PC
 */
@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Transactional
    public void crearComentario(String cuerpo, Long id_posteo) throws MiException {
        validar(cuerpo);
        Comentario comentario = new Comentario();

        comentario.setCuerpo(cuerpo);
        comentario.setId_posteo(id_posteo);
        comentario.setFecha(LocalDate.now());

        comentarioRepositorio.save(comentario);

    }

    public List<Comentario> listarComentariosPorIdPosteos(Long id) {

        return comentarioRepositorio.buscarPorIdPosteos(id);

    }
    
    @Transactional
    public void actualizar(Long id, String cuerpo, Long id_posteo, LocalDate fecha){
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Comentario comentario = respuesta.get();
            
            comentario.setCuerpo(cuerpo);
            comentario.setId_posteo(id_posteo);
            comentario.setFecha(fecha.now());
            comentarioRepositorio.save(comentario);
        }
    }
    
    @Transactional
    public void borrar(Long id, LocalDate fecha){
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Comentario comentario = respuesta.get();
            
            comentario.setFecha(fecha.now());
            comentario.setBorrado(true);
            comentarioRepositorio.save(comentario);
        }   
    }

    private void validar(String cuerpo) throws MiException {

        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("El comentario no puede estar vacío");
        }

    }
}
