/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.AppRECC.repositorios;

import com.egg.AppRECC.entidades.Posteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PC
 */

@Repository
public interface PosteoRepositorio extends JpaRepository<Posteo, Long>{
    
}
