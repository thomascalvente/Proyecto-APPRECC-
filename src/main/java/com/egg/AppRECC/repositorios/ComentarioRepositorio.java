/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.repositorios;

import com.egg.AppRECC.entidades.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PC
 */
@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long>{
    
    @Query("SELECT c FROM Comentario c WHERE c.borrado!=true AND c.id_posteo = :id ORDER BY c.fecha desc")
    public List<Comentario> buscarPorIdPosteos(@Param("id") Long id);
    
}
