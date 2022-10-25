/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.repositorios;

import com.egg.AppRECC.entidades.Campania;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author PC
 */
public interface CampaniaRepositorio extends JpaRepository<Campania, Long>{
    
    @Query("SELECT c FROM Campania c WHERE c.titulo = :titulo")
    public Campania buscarPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT c FROM Campania c WHERE c.borrado!=true ORDER BY c.fecha desc")
    public List<Campania> listarCampanias();
    
    @Query("SELECT c FROM Campania c WHERE c.id = :id")
    public Campania buscarPorId(@Param("id") Long id);
    
    @Query("SELECT c FROM Campania c WHERE c.borrado!=true and c.cuerpo LIKE %:frase% and c.titulo LIKE %:frase% ORDER BY c.fecha desc")
    public List<Campania> buscarCampanias(@Param("frase") String frase);
    
}
